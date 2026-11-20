package pt.ecl.portal.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

// Tudo o que é de gestão fica em /admin/... e este filtro protege tudo de uma vez.
@WebFilter("/admin/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) rq;
        HttpServletResponse resp = (HttpServletResponse) rs;
        HttpSession sessao = req.getSession(false);
        if (sessao == null || sessao.getAttribute("utilizador") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        resp.setHeader("Cache-Control", "no-store");    // o botão «voltar» não mostra páginas depois do logout
        chain.doFilter(rq, rs);
    }
}
