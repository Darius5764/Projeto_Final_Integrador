package pt.ecl.portal.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sessao = req.getSession(false);
        if (sessao != null) sessao.invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
