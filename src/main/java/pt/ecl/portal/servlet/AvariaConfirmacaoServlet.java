package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.OcorrenciaDAO;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/avarias/confirmacao")
public class AvariaConfirmacaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessao = req.getSession(false);
        Object id = sessao == null ? null : sessao.getAttribute("avariaCriada");
        if (id == null) { resp.sendRedirect(req.getContextPath() + "/avarias"); return; }
        sessao.removeAttribute("avariaCriada");
        try { req.setAttribute("o", new OcorrenciaDAO().procurar((Long) id)); }
        catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/avarias/confirmacao.jsp").forward(req, resp);
    }
}
