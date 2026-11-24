package pt.ecl.portal.dao;

import pt.ecl.portal.db.Db;
import pt.ecl.portal.model.PreInscricao;
import java.sql.*;
import java.util.*;

public class PreInscricaoDAO {

    public long inserir(int idCurso, String nome, String email, String telefone, String observacoes) throws SQLException {
        String sql = "INSERT INTO pre_inscricao (id_curso, nome, email, telefone, observacoes) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCurso); ps.setString(2, nome); ps.setString(3, email);
            ps.setString(4, telefone); ps.setString(5, observacoes);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return rs.getLong(1); }
        }
    }


    public List<PreInscricao> listar(Integer idCurso, String estado) throws SQLException {
        String sql = "SELECT p.*, c.nome AS nome_curso FROM pre_inscricao p JOIN curso c ON c.id_curso = p.id_curso"
                   + " WHERE (? IS NULL OR p.id_curso = ?) AND (? IS NULL OR p.estado = ?)"
                   + " ORDER BY p.criado_em DESC";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idCurso); ps.setObject(2, idCurso);
            ps.setString(3, estado); ps.setString(4, estado);
            List<PreInscricao> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PreInscricao p = new PreInscricao();
                    p.setId(rs.getLong("id"));
                    p.setIdCurso(rs.getInt("id_curso"));
                    p.setNomeCurso(rs.getString("nome_curso"));
                    p.setNome(rs.getString("nome"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    p.setObservacoes(rs.getString("observacoes"));
                    p.setEstado(rs.getString("estado"));
                    p.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
                    lista.add(p);
                }
            }
            return lista;
        }
    }


    public boolean mudarEstado(long id, String novoEstado, int idUtilizador) throws SQLException {
        try (Connection c = Db.ligar()) {
            c.setAutoCommit(false);
            try (PreparedStatement sel = c.prepareStatement("SELECT estado FROM pre_inscricao WHERE id = ? FOR UPDATE");
                 PreparedStatement upd = c.prepareStatement("UPDATE pre_inscricao SET estado = ? WHERE id = ?");
                 PreparedStatement his = c.prepareStatement(
                     "INSERT INTO historico_estado (tipo, id_registo, estado_anterior, estado_novo, id_utilizador)"
                     + " VALUES ('PRE_INSCRICAO', ?, ?, ?, ?)")) {
                sel.setLong(1, id);
                String anterior;
                try (ResultSet rs = sel.executeQuery()) {
                    if (!rs.next()) { c.rollback(); return false; }
                    anterior = rs.getString(1);
                }
                upd.setString(1, novoEstado); upd.setLong(2, id); upd.executeUpdate();
                his.setLong(1, id); his.setString(2, anterior); his.setString(3, novoEstado); his.setInt(4, idUtilizador);
                his.executeUpdate();
                c.commit();
                return true;
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }


    public Map<String, Integer> contarPorEstado() throws SQLException {
        return contar("SELECT estado, COUNT(*) FROM pre_inscricao GROUP BY estado");
    }

    static Map<String, Integer> contar(String sql) throws SQLException {
        Map<String, Integer> mapa = new LinkedHashMap<>();
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) mapa.put(rs.getString(1), rs.getInt(2));
        }
        return mapa;
    }
}
