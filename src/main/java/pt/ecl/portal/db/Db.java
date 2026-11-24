package pt.ecl.portal.db;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.*;
import java.util.Properties;

public final class Db {
    private static final Properties CONFIG = new Properties();

    static {

        Path ficheiro = Path.of(System.getProperty("catalina.base"), "conf", "portal-ecl.properties");
        try (InputStream in = Files.newInputStream(ficheiro)) {
            CONFIG.load(in);
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Configuração da BD em falta ou inválida: " + ficheiro);
        }
    }

    private Db() { }

    public static Connection ligar() throws SQLException {
        return DriverManager.getConnection(
                CONFIG.getProperty("db.url"),
                CONFIG.getProperty("db.user"),
                CONFIG.getProperty("db.password"));
    }
}
