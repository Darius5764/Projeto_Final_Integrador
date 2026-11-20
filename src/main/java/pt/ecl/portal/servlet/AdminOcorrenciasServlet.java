package pt.ecl.portal.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pt.ecl.portal.dao.OcorrenciaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/admin/ocorrencias")
public class AdminOcorrenciasServlet extends HttpServlet {
    private static final Set<String> ESTADOS = Set.of("ABERTA", "EM_ANALISE", "EM_RESOLUCAO", "RESOLVIDA");
    private static final Set<String> PRIORIDADES = Set.of("BAIXA", "NORMAL", "ELEVADA");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer sala = null;
        try { sala = Integer.valueOf(req.getParameter("sala")); } catch (NumberFormatException e) { /* todas */ }
        String estado = valido(ESTADOS, req.getParameter("estado"));
        String prioridade = valido(PRIORIDADES, req.getParameter("prioridade"));
        OcorrenciaDAO dao = new OcorrenciaDAO();
        try {
            req.setAttribute("salas", dao.listarSalas());
            req.setAttribute("lista", dao.listar(sala, estado, prioridade));
        } catch (SQLException e) { throw new ServletException(e); }
        req.getRequestDispatcher("/WEB-INF/jsp/admin/ocorrencias.jsp").forward(req, resp);
    }

    // Só aceita valores da lista fechada; qualquer outro valor (ou nenhum) = sem filtro
    private static String valido(Set<String> lista, String valor) {
        return valor != null && lista.contains(valor) ? valor : null;
    }
}
