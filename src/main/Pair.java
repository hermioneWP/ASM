package main;

public class Pair {
    private Autor first;
    private Autor second;
    private int weight;

    public int getWeight() {
        return weight;

    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Autor getFirst() {
        return first;
    }

    public void setFirst(Autor first) {
        this.first = first;
    }

    public Autor getSecond() {
        return second;
    }

    public void setSecond(Autor second) {
        this.second = second;
    }

    public Pair(Autor first, Autor second) {
        this.first = first;
        this.second = second;
        weight = 1;
    }

    public void addWeight() {
        weight++;
    }
}
