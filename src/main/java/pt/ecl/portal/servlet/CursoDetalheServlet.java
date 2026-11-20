package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.CursoDAO;
import pt.ecl.portal.model.Curso;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cursos/detalhe")
public class CursoDetalheServlet extends HttpServlet {
    private final CursoDAO dao = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Curso curso = null;
        try {
            curso = dao.procurar(Integer.parseInt(req.getParameter("id")));
        } catch (NumberFormatException e) { /* id inválido: fica null */ }
        catch (SQLException e) { throw new ServletException(e); }

        if (curso == null) { resp.sendError(404); return; }
        req.setAttribute("curso", curso);
        req.getRequestDispatcher("/WEB-INF/jsp/cursos/detalhe.jsp").forward(req, resp);
    }
}
