package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/resumo")
public class AdminResumoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("preinscricoes", new PreInscricaoDAO().contarPorEstado());
            req.setAttribute("ocorrencias", new OcorrenciaDAO().contarPorEstado());
        } catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/admin/resumo.jsp").forward(req, resp);
    }
}
