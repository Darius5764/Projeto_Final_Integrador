package pt.ecl.portal.dao;

import pt.ecl.portal.db.Db;
import pt.ecl.portal.model.Utilizador;
import java.sql.*;

public class UtilizadorDAO {


    public Utilizador procurarGestor(String identificador) throws SQLException {
        String sql = "SELECT * FROM utilizador WHERE identificador = ? AND ativo = TRUE"
                   + " AND perfil IN ('GESTOR','TECNICO')";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, identificador);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Utilizador u = new Utilizador();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setIdentificador(rs.getString("identificador"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setPerfil(rs.getString("perfil"));
                return u;
            }
        }
    }
}
