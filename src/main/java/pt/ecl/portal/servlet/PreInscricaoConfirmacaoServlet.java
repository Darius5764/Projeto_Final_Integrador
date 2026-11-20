package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/preinscricao/confirmacao")
public class PreInscricaoConfirmacaoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessao = req.getSession(false);
        Object id = sessao == null ? null : sessao.getAttribute("pedidoCriado");
        if (id == null) { resp.sendRedirect(req.getContextPath() + "/preinscricao"); return; }
        sessao.removeAttribute("pedidoCriado");          // só se mostra uma vez
        req.setAttribute("numero", id);
        req.getRequestDispatcher("/WEB-INF/jsp/preinscricao/confirmacao.jsp").forward(req, resp);
    }
}
