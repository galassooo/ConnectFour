package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectFourPlayer extends PlayerBusiness implements ConnectFourPlayerInterface, Cloneable {
    @JsonIgnore
    private SymbolInterface symbol;

    @JsonIgnore
    private String color;

    /* constructor */
    @JsonIgnore
    public ConnectFourPlayer(String name, String color, SymbolInterface symbol) {
        super(name);
        this.color = color;
        this.symbol = symbol;
    }

    @JsonCreator
    private ConnectFourPlayer(@JsonProperty("name") String name, @JsonProperty("color") String color) {
        super(name);
        this.color = color;
    }

    /* getters */

    @Override
    @JsonIgnore // For getters, this tells Jackson not to use it to serialize whatever the getter is getting
    public SymbolInterface getSymbol() {
        return ((SymbolBusiness) symbol).clone();
    }

    @Override
    @JsonIgnore
    public String getColor() {
        return color;
    }

    /* setters */

    @Override
    @JsonIgnore
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    @JsonIgnore
    public void setSymbol(SymbolInterface symbol) {
        if (symbol == null)
            return;
        this.symbol = ((SymbolBusiness) symbol).clone();
    }

    @Override
    public ConnectFourPlayer clone() {
        ConnectFourPlayer clone = (ConnectFourPlayer) super.clone();
        if (clone != null) {
            clone.symbol = (symbol != null) ? ((SymbolBusiness) symbol).clone() : null;
            return clone;
        }
        return null;
    }
}