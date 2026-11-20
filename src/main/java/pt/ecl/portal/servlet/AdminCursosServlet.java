package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.CursoDAO;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/cursos")
public class AdminCursosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try { req.setAttribute("cursos", new CursoDAO().listar(null, null)); }
        catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/admin/cursos.jsp").forward(req, resp);
    }
}
