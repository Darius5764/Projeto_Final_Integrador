package pt.ecl.portal.model;

public class Sala {
    private int id;
    private String codigo;
    private String edificio;
    private String piso;

    public Sala() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    public String getPiso() { return piso; }
    public void setPiso(String piso) { this.piso = piso; }
}
