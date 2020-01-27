package main;

public class Autor {
    private String ime;
    private int id;
    private int productivity;

    public Autor(int id, String ime) {
        this.ime = ime;
        this.id = id;
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
}
