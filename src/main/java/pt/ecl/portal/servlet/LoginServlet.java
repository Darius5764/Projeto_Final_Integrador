package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.UtilizadorDAO;
import pt.ecl.portal.model.Utilizador;
import pt.ecl.portal.util.Passwords;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UtilizadorDAO utilizadores = new UtilizadorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String identificador = req.getParameter("identificador") == null ? "" : req.getParameter("identificador").trim();
        String pw = req.getParameter("password") == null ? "" : req.getParameter("password");
        Utilizador u;
        try { u = utilizadores.procurarGestor(identificador); }
        catch (SQLException e) { throw new ServletException(e); }

        if (u == null || !Passwords.verificar(pw, u.getPasswordHash())) {
            req.setAttribute("erro", "Identificador ou password incorretos.");    // mesma mensagem nos dois casos
            req.getRequestDispatcher("/WEB-INF/jsp/admin/login.jsp").forward(req, resp);
            return;
        }
        u.setPasswordHash(null);                       // o hash não fica na sessão
        req.getSession(true);
        req.changeSessionId();                         // evita fixação de sessão
        req.getSession().setAttribute("utilizador", u);
        resp.sendRedirect(req.getContextPath() + "/admin/resumo");
    }
}
