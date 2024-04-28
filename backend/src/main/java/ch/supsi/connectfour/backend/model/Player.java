package ch.supsi.connectfour.backend.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public final class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 4278903726155278393L;
    private String name;
    private int numWin;

    public Player(String name, int numWin) {  //delego il controllo ai setters
        setName(name);
        setNumWin(numWin);
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null || name.isEmpty() || name.isBlank() ? "not available" : name;
    }

    public int getNumWin() {
        return numWin;
    }

    public void setNumWin(int numWin) {
        this.numWin =Math.max(0, numWin);
    }

    //solo per test
    @Override
    public String toString() {
        return "name: " + name  +
                ", win: " + numWin;
    }

    //todo: considerare un modo per rendere univoco ogni player
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return getNumWin() == player.getNumWin() && Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumWin());
    }
}
