package pt.ecl.portal.model;

import java.time.LocalDateTime;

public class Ocorrencia {
    private long id;
    private String nomeReportante;
    private String perfilReportante;
    private int idSala;
    private String salaCodigo;
    private String categoria;
    private String descricao;
    private boolean impedeAula;
    private String prioridade;
    private String estado;
    private String resolucao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime resolvidoEm;

    public Ocorrencia() { }

    // Código visível do pedido, ex.: AV-00012
    public String getCodigo() { return String.format("AV-%05d", id); }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNomeReportante() { return nomeReportante; }
    public void setNomeReportante(String nomeReportante) { this.nomeReportante = nomeReportante; }
    public String getPerfilReportante() { return perfilReportante; }
    public void setPerfilReportante(String perfilReportante) { this.perfilReportante = perfilReportante; }
    public int getIdSala() { return idSala; }
    public void setIdSala(int idSala) { this.idSala = idSala; }
    public String getSalaCodigo() { return salaCodigo; }
    public void setSalaCodigo(String salaCodigo) { this.salaCodigo = salaCodigo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public boolean isImpedeAula() { return impedeAula; }
    public void setImpedeAula(boolean impedeAula) { this.impedeAula = impedeAula; }
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getResolucao() { return resolucao; }
    public void setResolucao(String resolucao) { this.resolucao = resolucao; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
    public LocalDateTime getResolvidoEm() { return resolvidoEm; }
    public void setResolvidoEm(LocalDateTime resolvidoEm) { this.resolvidoEm = resolvidoEm; }

    public String getCriadoEmTexto() { return pt.ecl.portal.util.Datas.texto(criadoEm); }
    public String getAtualizadoEmTexto() { return pt.ecl.portal.util.Datas.texto(atualizadoEm); }
    public String getResolvidoEmTexto() { return pt.ecl.portal.util.Datas.texto(resolvidoEm); }
}
