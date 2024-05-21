package ch.supsi.connectfour.backend.business.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public final class PlayerModel implements Cloneable{
    // TODO: FOR TESTING PURPOSES
    @JsonIgnore
    public int id;
    private static int ids = 0;
    @JsonInclude
    private String name;
    @JsonInclude
    private int numWin;

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name, @JsonProperty(value = "numWin") int numWin){
        this.id = ids + 1;
        ids++;
        this.name = name;
        this.numWin = numWin;
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
        // TODO: REMOVE THIS. Needed for testing
        final boolean isEqual = getNumWin() == player.getNumWin() &&
                Objects.equals(getName(), player.getName());

        System.out.println("Equals " + isEqual);
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumWin());
    }
}
