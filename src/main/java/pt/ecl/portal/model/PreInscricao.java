package pt.ecl.portal.model;

import java.time.LocalDateTime;

public class PreInscricao {
    private long id;
    private int idCurso;
    private String nomeCurso;
    private String nome;
    private String email;
    private String telefone;
    private String observacoes;
    private String estado;
    private LocalDateTime criadoEm;

    public PreInscricao() { }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public String getCriadoEmTexto() { return pt.ecl.portal.util.Datas.texto(criadoEm); }
}
