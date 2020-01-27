package main;

public class Autor {
    private String ime;
    private int id;
    private int productivity;
    private String fakultet,katedra;

    public Autor(String ime, int id, String fakultet, String katedra) {
        this.ime = ime;
        this.id = id;
        this.fakultet = fakultet;
        this.katedra = katedra;
        productivity = 0;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public void addProductivity(){
        productivity++;
    }

    public String getFakultet() {
        return fakultet;
    }

    public void setFakultet(String fakultet) {
        this.fakultet = fakultet;
    }

    public String getKatedra() {
        return katedra;
    }

    public void setKatedra(String katedra) {
        this.katedra = katedra;
    }
}
