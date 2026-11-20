package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.OcorrenciaDAO;
import pt.ecl.portal.model.Ocorrencia;
import pt.ecl.portal.model.Utilizador;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/admin/ocorrencias/detalhe")
public class AdminOcorrenciaDetalheServlet extends HttpServlet {
    private static final Set<String> ESTADOS = Set.of("ABERTA", "EM_ANALISE", "EM_RESOLUCAO", "RESOLVIDA");
    private final OcorrenciaDAO dao = new OcorrenciaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mostrar(req, resp, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String novo = req.getParameter("estado");
        String resolucao = req.getParameter("resolucao") == null ? "" : req.getParameter("resolucao").trim();
        long id;
        try { id = Long.parseLong(req.getParameter("id")); }
        catch (NumberFormatException e) { resp.sendError(404); return; }

        if (novo == null || !ESTADOS.contains(novo)) { mostrar(req, resp, "Estado inválido."); return; }
        if (novo.equals("RESOLVIDA") && resolucao.isEmpty()) {
            mostrar(req, resp, "Para marcar como Resolvida, preencha a resolução."); return;
        }
        Utilizador u = (Utilizador) req.getSession().getAttribute("utilizador");
        try {
            if (!dao.mudarEstado(id, novo, resolucao.isEmpty() ? null : resolucao, u.getId())) {
                resp.sendError(404); return;
            }
        } catch (SQLException e) { throw new ServletException(e); }
        resp.sendRedirect(req.getContextPath() + "/admin/ocorrencias/detalhe?id=" + id);
    }

    private void mostrar(HttpServletRequest req, HttpServletResponse resp, String erro) throws ServletException, IOException {
        Ocorrencia o = null;
        try {
            long id = Long.parseLong(req.getParameter("id"));
            o = dao.procurar(id);
            if (o != null) req.setAttribute("historico", dao.historico(id));
        } catch (NumberFormatException e) { /* id inválido */ }
        catch (SQLException e) { throw new ServletException(e); }
        if (o == null) { resp.sendError(404); return; }
        req.setAttribute("o", o);
        req.setAttribute("erro", erro);
        req.getRequestDispatcher("/WEB-INF/jsp/admin/ocorrencia-detalhe.jsp").forward(req, resp);
    }
}
