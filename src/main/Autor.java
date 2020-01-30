package main;

public class Autor implements Comparable<Autor> {
    private String ime;
    private int id;
    private int productivity;
    private String fakultet, katedra;
    private String imePrezime;

    public Autor(String ime, int id, String fakultet, String katedra,String imePrezime) {
        this.ime = ime;
        this.id = id;
        this.fakultet = fakultet;
        this.katedra = katedra;
        this.imePrezime = imePrezime;
        productivity = 0;
    }

    public String getIme() {
        return ime;
    }

    public int getId() {
        return id;
    }

    public int getProductivity() {
        return productivity;
    }

    public void addProductivity() {
        productivity++;
    }

    public String getFakultet() {
        return fakultet;
    }

    public String getKatedra() {
        return katedra;
    }

    @Override
    public int compareTo(Autor autor) {
        return autor.productivity - productivity;
    }

    public String getImePrezime() {
        return imePrezime;
    }
}
