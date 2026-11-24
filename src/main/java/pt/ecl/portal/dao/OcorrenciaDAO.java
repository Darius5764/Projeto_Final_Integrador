package pt.ecl.portal.dao;

import pt.ecl.portal.db.Db;
import pt.ecl.portal.model.Ocorrencia;
import pt.ecl.portal.model.Sala;
import pt.ecl.portal.util.Datas;
import java.sql.*;
import java.util.*;

public class OcorrenciaDAO {

    public long inserir(Ocorrencia o) throws SQLException {
        String sql = "INSERT INTO ocorrencia (nome_reportante, perfil_reportante, id_sala, categoria, descricao,"
                   + " impede_aula, prioridade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getNomeReportante());
            ps.setString(2, o.getPerfilReportante());
            ps.setInt(3, o.getIdSala());
            ps.setString(4, o.getCategoria());
            ps.setString(5, o.getDescricao());
            ps.setBoolean(6, o.isImpedeAula());
            ps.setString(7, o.getPrioridade());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return rs.getLong(1); }
        }
    }

    public Ocorrencia procurar(long id) throws SQLException {
        String sql = "SELECT o.*, s.codigo AS sala_codigo FROM ocorrencia o JOIN sala s ON s.id = o.id_sala WHERE o.id = ?";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? ler(rs) : null; }
        }
    }


    public List<Ocorrencia> listar(Integer idSala, String estado, String prioridade) throws SQLException {
        String sql = "SELECT o.*, s.codigo AS sala_codigo FROM ocorrencia o JOIN sala s ON s.id = o.id_sala"
                   + " WHERE (? IS NULL OR o.id_sala = ?) AND (? IS NULL OR o.estado = ?)"
                   + " AND (? IS NULL OR o.prioridade = ?)"
                   + " ORDER BY FIELD(o.prioridade, 'ELEVADA', 'NORMAL', 'BAIXA'), o.criado_em DESC";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idSala); ps.setObject(2, idSala);
            ps.setString(3, estado); ps.setString(4, estado);
            ps.setString(5, prioridade); ps.setString(6, prioridade);
            List<Ocorrencia> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(ler(rs));
            }
            return lista;
        }
    }


    public boolean mudarEstado(long id, String novoEstado, String resolucao, int idUtilizador) throws SQLException {
        try (Connection c = Db.ligar()) {
            c.setAutoCommit(false);
            try (PreparedStatement sel = c.prepareStatement("SELECT estado FROM ocorrencia WHERE id = ? FOR UPDATE");
                 PreparedStatement upd = c.prepareStatement(
                     "UPDATE ocorrencia SET estado = ?, resolucao = COALESCE(?, resolucao),"
                     + " resolvido_em = IF(? = 'RESOLVIDA', NOW(), NULL) WHERE id = ?");
                 PreparedStatement his = c.prepareStatement(
                     "INSERT INTO historico_estado (tipo, id_registo, estado_anterior, estado_novo, id_utilizador)"
                     + " VALUES ('OCORRENCIA', ?, ?, ?, ?)")) {
                sel.setLong(1, id);
                String anterior;
                try (ResultSet rs = sel.executeQuery()) {
                    if (!rs.next()) { c.rollback(); return false; }
                    anterior = rs.getString(1);
                }
                upd.setString(1, novoEstado); upd.setString(2, resolucao);
                upd.setString(3, novoEstado); upd.setLong(4, id);
                upd.executeUpdate();
                his.setLong(1, id); his.setString(2, anterior); his.setString(3, novoEstado); his.setInt(4, idUtilizador);
                his.executeUpdate();
                c.commit();
                return true;
            } catch (SQLException e) { c.rollback(); throw e; }
        }
    }


    public List<Map<String, Object>> historico(long id) throws SQLException {
        String sql = "SELECT h.estado_anterior, h.estado_novo, h.alterado_em, u.nome FROM historico_estado h"
                   + " JOIN utilizador u ON u.id = h.id_utilizador"
                   + " WHERE h.tipo = 'OCORRENCIA' AND h.id_registo = ? ORDER BY h.alterado_em, h.id";
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            List<Map<String, Object>> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> linha = new HashMap<>();
                    linha.put("anterior", rs.getString("estado_anterior"));
                    linha.put("novo", rs.getString("estado_novo"));
                    linha.put("quando", Datas.texto(rs.getTimestamp("alterado_em").toLocalDateTime()));
                    linha.put("quem", rs.getString("nome"));
                    lista.add(linha);
                }
            }
            return lista;
        }
    }

    public Map<String, Integer> contarPorEstado() throws SQLException {
        return PreInscricaoDAO.contar("SELECT estado, COUNT(*) FROM ocorrencia GROUP BY estado");
    }

    public List<Sala> listarSalas() throws SQLException {
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement("SELECT * FROM sala ORDER BY codigo");
             ResultSet rs = ps.executeQuery()) {
            List<Sala> lista = new ArrayList<>();
            while (rs.next()) {
                Sala s = new Sala();
                s.setId(rs.getInt("id"));
                s.setCodigo(rs.getString("codigo"));
                s.setEdificio(rs.getString("edificio"));
                s.setPiso(rs.getString("piso"));
                lista.add(s);
            }
            return lista;
        }
    }

    public boolean salaExiste(int id) throws SQLException {
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement("SELECT 1 FROM sala WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    private Ocorrencia ler(ResultSet rs) throws SQLException {
        Ocorrencia o = new Ocorrencia();
        o.setId(rs.getLong("id"));
        o.setNomeReportante(rs.getString("nome_reportante"));
        o.setPerfilReportante(rs.getString("perfil_reportante"));
        o.setIdSala(rs.getInt("id_sala"));
        o.setSalaCodigo(rs.getString("sala_codigo"));
        o.setCategoria(rs.getString("categoria"));
        o.setDescricao(rs.getString("descricao"));
        o.setImpedeAula(rs.getBoolean("impede_aula"));
        o.setPrioridade(rs.getString("prioridade"));
        o.setEstado(rs.getString("estado"));
        o.setResolucao(rs.getString("resolucao"));
        o.setCriadoEm(rs.getTimestamp("criado_em").toLocalDateTime());
        Timestamp t = rs.getTimestamp("atualizado_em");
        o.setAtualizadoEm(t == null ? null : t.toLocalDateTime());
        t = rs.getTimestamp("resolvido_em");
        o.setResolvidoEm(t == null ? null : t.toLocalDateTime());
        return o;
    }
}
