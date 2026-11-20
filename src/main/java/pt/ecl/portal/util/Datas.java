package pt.ecl.portal.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Datas {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Datas() { }

    // Data e hora no formato português; vazio se não houver data
    public static String texto(LocalDateTime d) { return d == null ? "" : d.format(FORMATO); }
}
