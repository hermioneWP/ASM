package main;

public class Autor {
    private String ime;
    private int id;

    public Autor(int id,String ime ) {
        this.ime = ime;
        this.id = id;
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
}