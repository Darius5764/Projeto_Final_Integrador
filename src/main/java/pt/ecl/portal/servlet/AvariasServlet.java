package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.OcorrenciaDAO;
import pt.ecl.portal.model.Ocorrencia;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/avarias")
public class AvariasServlet extends HttpServlet {
    private static final Set<String> PERFIS = Set.of("FORMANDO", "FORMADOR");
    private static final Set<String> CATEGORIAS =
            Set.of("PC", "PROJETOR", "REDE", "WIFI", "IMPRESSORA", "SOFTWARE", "MOBILIARIO", "OUTRO");
    private final OcorrenciaDAO dao = new OcorrenciaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mostrar(req, resp, new HashMap<>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = limpar(req.getParameter("nome"));
        String perfil = limpar(req.getParameter("perfil"));
        String categoria = limpar(req.getParameter("categoria"));
        String descricao = limpar(req.getParameter("descricao"));
        boolean impedeAula = req.getParameter("impedeAula") != null;
        Map<String, String> erros = new HashMap<>();

        if (nome.length() < 3 || nome.length() > 100) erros.put("nome", "Indique o nome (3 a 100 caracteres).");
        if (!PERFIS.contains(perfil)) erros.put("perfil", "Escolha Formando ou Formador.");
        if (!CATEGORIAS.contains(categoria)) erros.put("categoria", "Escolha um equipamento da lista.");
        if (descricao.length() < 10 || descricao.length() > 1000) erros.put("descricao", "Descreva o problema (10 a 1000 caracteres).");

        int idSala = -1;
        try { idSala = Integer.parseInt(req.getParameter("sala")); } catch (NumberFormatException e) { /* fica -1 */ }
        try {
            if (idSala < 1 || !dao.salaExiste(idSala)) erros.put("sala", "Escolha uma sala da lista.");
            if (!erros.isEmpty()) { mostrar(req, resp, erros); return; }

            Ocorrencia o = new Ocorrencia();
            o.setNomeReportante(nome);
            o.setPerfilReportante(perfil);
            o.setIdSala(idSala);
            o.setCategoria(categoria);
            o.setDescricao(descricao);
            o.setImpedeAula(impedeAula);
            o.setPrioridade(prioridade(categoria, impedeAula));
            long id = dao.inserir(o);
            req.getSession().setAttribute("avariaCriada", id);
            resp.sendRedirect(req.getContextPath() + "/avarias/confirmacao");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


    static String prioridade(String categoria, boolean impedeAula) {
        if (impedeAula) return "ELEVADA";
        if (categoria.equals("MOBILIARIO") || categoria.equals("OUTRO")) return "BAIXA";
        return "NORMAL";
    }

    private void mostrar(HttpServletRequest req, HttpServletResponse resp, Map<String, String> erros)
            throws ServletException, IOException {
        try { req.setAttribute("salas", dao.listarSalas()); }
        catch (SQLException e) { throw new ServletException(e); }
        req.setAttribute("erros", erros);
        req.getRequestDispatcher("/WEB-INF/jsp/avarias/formulario.jsp").forward(req, resp);
    }

    private static String limpar(String s) { return s == null ? "" : s.trim(); }
}
