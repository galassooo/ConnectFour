package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class PlayerModel implements Cloneable {
    @JsonInclude
    private String name;

    //TODO DA MODIFICARE! SERVIVA PER TESTARE E NON CE ANCORA IL CARICAMENTO DELLE PREFERENZE
    private String preferenceColor;
    private Symbol symbol;

    public PlayerModel(String name, int numWin) {  //delego il controllo ai setters
        setName(name);
    }


    public PlayerModel(String name, String preferenceColor, Symbol symbol) {
        setName(name);
        this.preferenceColor = preferenceColor;
        this.symbol = symbol;
    }

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name) {
        this.name = name;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null || name.isEmpty() || name.isBlank() ? "not available" : name;
    }

    public String getPreferenceColor() {
        return preferenceColor;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public Object clone() {
        try {
            PlayerModel cloned = (PlayerModel) super.clone();
            cloned.symbol = this.symbol;
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
