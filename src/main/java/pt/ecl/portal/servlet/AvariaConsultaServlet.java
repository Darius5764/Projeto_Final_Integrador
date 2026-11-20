package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.OcorrenciaDAO;
import pt.ecl.portal.model.Ocorrencia;
import java.io.IOException;
import java.sql.SQLException;

// Consulta pública: só mostra dados técnicos, nunca o nome de quem reportou, e não altera nada.
@WebServlet("/avarias/consultar")
public class AvariaConsultaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codigo = req.getParameter("codigo");
        if (codigo != null && !codigo.isBlank()) {
            Ocorrencia o = null;
            try {
                long id = Long.parseLong(codigo.trim().replaceFirst("(?i)^AV-", ""));
                o = new OcorrenciaDAO().procurar(id);
            } catch (NumberFormatException e) { /* código inválido */ }
            catch (SQLException e) { throw new ServletException(e); }
            if (o == null) req.setAttribute("naoEncontrado", true);
            else req.setAttribute("o", o);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/avarias/consultar.jsp").forward(req, resp);
    }
}
