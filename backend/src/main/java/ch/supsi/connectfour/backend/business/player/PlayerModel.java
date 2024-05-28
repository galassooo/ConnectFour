package ch.supsi.connectfour.backend.business.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

class PlayerModel implements Cloneable, PlayerBusinessInterface{
    @JsonInclude
    private String name;
    @JsonInclude
    private int id;

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }


    @Override
    public @Nullable Object clone() {
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
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "name: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerModel that)) return false;
        return getId() == that.getId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId());
    }
}
