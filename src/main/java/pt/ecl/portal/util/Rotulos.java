package pt.ecl.portal.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.Map;

// Na BD os estados ficam sem acentos (EM_ANALISE); nas páginas usa-se ${rotulo[x.estado]}.
@WebListener
public class Rotulos implements ServletContextListener {
    public static final Map<String, String> TEXTO = Map.ofEntries(
            Map.entry("RECEBIDA", "Recebida"),
            Map.entry("EM_ANALISE", "Em análise"),
            Map.entry("CONTACTADA", "Contactada"),
            Map.entry("ABERTA", "Aberta"),
            Map.entry("EM_RESOLUCAO", "Em resolução"),
            Map.entry("RESOLVIDA", "Resolvida"),
            Map.entry("BAIXA", "Baixa"),
            Map.entry("NORMAL", "Normal"),
            Map.entry("ELEVADA", "Elevada"),
            Map.entry("FORMANDO", "Formando"),
            Map.entry("FORMADOR", "Formador"),
            Map.entry("PC", "PC"),
            Map.entry("PROJETOR", "Projetor"),
            Map.entry("REDE", "Rede"),
            Map.entry("WIFI", "Wi-Fi"),
            Map.entry("IMPRESSORA", "Impressora"),
            Map.entry("SOFTWARE", "Software"),
            Map.entry("MOBILIARIO", "Mobiliário"),
            Map.entry("OUTRO", "Outro"));

    @Override
    public void contextInitialized(ServletContextEvent e) {
        e.getServletContext().setAttribute("rotulo", TEXTO);
    }
}
