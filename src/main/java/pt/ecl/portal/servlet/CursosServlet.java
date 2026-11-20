package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.CursoDAO;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cursos")
public class CursosServlet extends HttpServlet {
    private final CursoDAO dao = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("cursos", dao.listar(req.getParameter("q"), req.getParameter("modalidade")));
        } catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/cursos/lista.jsp").forward(req, resp);
    }
}
