package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

@WebServlet("/preinscricao")
public class PreInscricaoServlet extends HttpServlet {
    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONE = Pattern.compile("^(\\+351 ?)?[29]\\d{8}$");
    private final CursoDAO cursos = new CursoDAO();
    private final PreInscricaoDAO dao = new PreInscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mostrar(req, resp, new HashMap<>(), req.getParameter("curso"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = limpar(req.getParameter("nome"));
        String email = limpar(req.getParameter("email"));
        String telefone = limpar(req.getParameter("telefone"));
        String obs = limpar(req.getParameter("observacoes"));
        Map<String, String> erros = new HashMap<>();

        // Validação no servidor (o browser também valida, mas pode ser contornado)
        if (nome.length() < 3 || nome.length() > 120) erros.put("nome", "Indique o nome completo (mínimo 3 caracteres).");
        if (email.length() > 150 || !EMAIL.matcher(email).matches()) erros.put("email", "Indique um email válido.");
        if (!telefone.isEmpty() && !TELEFONE.matcher(telefone).matches()) erros.put("telefone", "Telefone inválido (9 dígitos).");
        if (obs.length() > 500) erros.put("observacoes", "Máximo de 500 caracteres.");
        if (req.getParameter("privacidade") == null) erros.put("privacidade", "Tem de confirmar a informação de privacidade.");

        int idCurso = -1;
        try { idCurso = Integer.parseInt(req.getParameter("curso")); } catch (NumberFormatException e) { /* fica -1 */ }
        try {
            if (idCurso < 1 || !cursos.existe(idCurso)) erros.put("curso", "Escolha um curso da lista.");
            if (!erros.isEmpty()) { mostrar(req, resp, erros, req.getParameter("curso")); return; }

            long id = dao.inserir(idCurso, nome, email, telefone.isEmpty() ? null : telefone, obs.isEmpty() ? null : obs);
            req.getSession().setAttribute("pedidoCriado", id);          // nada de dados pessoais no URL
            resp.sendRedirect(req.getContextPath() + "/preinscricao/confirmacao");   // POST → redirect → GET
        } catch (SQLException e) {
            throw new ServletException(e);       // vai para a página de erro genérica
        }
    }

    private void mostrar(HttpServletRequest req, HttpServletResponse resp, Map<String, String> erros, String curso)
            throws ServletException, IOException {
        try { req.setAttribute("cursos", cursos.listar(null, null)); }
        catch (SQLException e) { throw new ServletException(e); }
        req.setAttribute("erros", erros);
        try { req.setAttribute("cursoEscolhido", Integer.parseInt(curso)); } catch (Exception e) { /* nenhum */ }
        req.getRequestDispatcher("/WEB-INF/jsp/preinscricao/formulario.jsp").forward(req, resp);
    }

    private static String limpar(String s) { return s == null ? "" : s.trim(); }
}
