package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.*;
import pt.ecl.portal.model.Utilizador;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/admin/preinscricoes")
public class AdminPreInscricoesServlet extends HttpServlet {
    private static final Set<String> ESTADOS = Set.of("RECEBIDA", "EM_ANALISE", "CONTACTADA");
    private static final Set<String> NOVOS_ESTADOS = Set.of("EM_ANALISE", "CONTACTADA");
    private final PreInscricaoDAO dao = new PreInscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer curso = null;
        try { curso = Integer.valueOf(req.getParameter("curso")); } catch (NumberFormatException e) { /* todos */ }
        String filtro = req.getParameter("estado");
        String estado = filtro != null && ESTADOS.contains(filtro) ? filtro : null;
        try {
            req.setAttribute("cursos", new CursoDAO().listar(null, null));
            req.setAttribute("lista", dao.listar(curso, estado));
        } catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/admin/preinscricoes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String novo = req.getParameter("estado");
        Utilizador u = (Utilizador) req.getSession().getAttribute("utilizador");
        try {
            long id = Long.parseLong(req.getParameter("id"));
            if (novo != null && NOVOS_ESTADOS.contains(novo)) dao.mudarEstado(id, novo, u.getId());
        } catch (NumberFormatException e) { /* id inválido: ignora */ }
        catch (SQLException e) { throw new ServletException(e); }
        resp.sendRedirect(req.getContextPath() + "/admin/preinscricoes");
    }
}
