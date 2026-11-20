package pt.ecl.portal.model;

import java.time.LocalDate;

public class Curso {
    private int idCurso;
    private String nome;
    private String area;
    private String modalidade;
    private String nivel;
    private Integer horasTotal;          // null = «A confirmar»
    private String componentes;
    private String perfilSaida;
    private String saidasProfissionais;
    private String fonte;
    private LocalDate dataVerificacao;

    public Curso() { }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public Integer getHorasTotal() { return horasTotal; }
    public void setHorasTotal(Integer horasTotal) { this.horasTotal = horasTotal; }
    public String getComponentes() { return componentes; }
    public void setComponentes(String componentes) { this.componentes = componentes; }
    public String getPerfilSaida() { return perfilSaida; }
    public void setPerfilSaida(String perfilSaida) { this.perfilSaida = perfilSaida; }
    public String getSaidasProfissionais() { return saidasProfissionais; }
    public void setSaidasProfissionais(String saidasProfissionais) { this.saidasProfissionais = saidasProfissionais; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public LocalDate getDataVerificacao() { return dataVerificacao; }
    public void setDataVerificacao(LocalDate dataVerificacao) { this.dataVerificacao = dataVerificacao; }
}
