package pt.ecl.portal.dao;

import pt.ecl.portal.db.Db;
import pt.ecl.portal.model.Curso;
import java.sql.*;
import java.util.*;

public class CursoDAO {

    public List<Curso> listar(String pesquisa, String modalidade) throws SQLException {
        String sql = "SELECT * FROM curso WHERE ativo = TRUE"
                   + " AND (? IS NULL OR nome LIKE ? OR area LIKE ?)"
                   + " AND (? IS NULL OR modalidade = ?)"
                   + " ORDER BY area, nome";
        String like = (pesquisa == null || pesquisa.isBlank()) ? null : "%" + pesquisa.trim() + "%";
        String mod = (modalidade == null || modalidade.isBlank()) ? null : modalidade;
        try (Connection c = Db.ligar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, like); ps.setString(2, like); ps.setString(3, like);
            ps.setString(4, mod); ps.setString(5, mod);
            List<Curso> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(ler(rs));
            }
            return lista;
        }
    }

    public Curso procurar(int id) throws SQLException {
        try (Connection c = Db.ligar();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM curso WHERE id_curso = ? AND ativo = TRUE")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? ler(rs) : null; }
        }
    }

    public boolean existe(int id) throws SQLException { return procurar(id) != null; }

    private Curso ler(ResultSet rs) throws SQLException {
        Curso k = new Curso();
        k.setIdCurso(rs.getInt("id_curso"));
        k.setNome(rs.getString("nome"));
        k.setArea(rs.getString("area"));
        k.setModalidade(rs.getString("modalidade"));
        k.setNivel(rs.getString("nivel"));
        k.setHorasTotal((Integer) rs.getObject("horas_total"));   // pode ser null
        k.setComponentes(rs.getString("componentes"));
        k.setPerfilSaida(rs.getString("perfil_saida"));
        k.setSaidasProfissionais(rs.getString("saidas_profissionais"));
        k.setFonte(rs.getString("fonte"));
        k.setDataVerificacao(rs.getDate("data_verificacao").toLocalDate());
        return k;
    }
}
