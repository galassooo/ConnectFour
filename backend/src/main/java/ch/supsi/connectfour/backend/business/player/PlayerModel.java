package ch.supsi.connectfour.backend.business.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class PlayerModel implements Cloneable {
    @JsonInclude
    private final String name;

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name) {
        this.name = name;
    }

    //getters and setters
    public String getName() {
        return name;
    }


    @Override
    public Object clone() {
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
        return "name: " + name;
    }

    //todo: considerare un modo per rendere univoco ogni player
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerModel player)) return false;
        return Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
