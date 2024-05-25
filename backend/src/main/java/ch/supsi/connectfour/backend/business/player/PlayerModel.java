package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class PlayerModel implements Cloneable {
    @JsonInclude
    private String name;
    @JsonInclude
    private int id;

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name, @JsonProperty(value = "id") int id) {
        this.name = name;
        this.id = id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name == null || name.isEmpty() || name.isBlank() ? "not available" : name;
    }


    @Override
    public Object clone() {
        try {
            PlayerModel cloned = (PlayerModel) super.clone();
            cloned.id = this.id;
            cloned.name = this.name;
            return cloned;
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
