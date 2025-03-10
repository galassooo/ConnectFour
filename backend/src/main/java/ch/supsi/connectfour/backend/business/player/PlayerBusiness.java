package ch.supsi.connectfour.backend.business.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class PlayerBusiness implements Cloneable, PlayerBusinessInterface {

    /* fields */
    @JsonInclude
    private String name;
    @JsonInclude
    private int id;

    /* constructors */
    @JsonCreator
    public PlayerBusiness(@JsonProperty(value = "name") String name) {
        this.name = name;
    }

    /* getters */
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    /* overrides */
    @Override
    public @Nullable Object clone() {
        try {
            PlayerBusiness cloned = (PlayerBusiness) super.clone();
            cloned.id = this.id;
            cloned.name = this.name;
            return cloned;
        } catch (CloneNotSupportedException e) {
            System.err.println("Clone function is not supported in a Player's superclass");
        }
        return null;
    }

    // Only used in testing
    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return String.format("Name: %s", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerBusiness that)) return false;
        return getId() == that.getId() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getId());
    }
}
