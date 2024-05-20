package ch.supsi.connectfour.backend.business.player;

import java.util.Objects;

public final class PlayerModel implements Cloneable{
    private String name;
    private int numWin;

    public PlayerModel(String name, int numWin) {  //delego il controllo ai setters
        setName(name);
        setNumWin(numWin);
    }

    public PlayerModel(String name) {
        this.name = name;
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

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Clone function is not supported in a Player's superclass");
        }
        return null;
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
        if (!(o instanceof PlayerModel player)) return false;
        return getNumWin() == player.getNumWin() && Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumWin());
    }
}
