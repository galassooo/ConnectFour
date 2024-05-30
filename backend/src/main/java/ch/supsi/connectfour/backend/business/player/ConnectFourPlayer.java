package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectFourPlayer extends PlayerBusiness implements ConnectFourPlayerInterface, Cloneable {

    @JsonInclude
    private SymbolInterface symbol;

    @JsonInclude
    private final String color;

    /* constructor */
    @JsonIgnore
    public ConnectFourPlayer(String name, String color, SymbolInterface symbol) {
        super(name);
        this.color = color;
        this.symbol = symbol;
    }

    @JsonCreator
    private ConnectFourPlayer(@JsonProperty("name") String name,
                              @JsonProperty("color") String color,
                              @JsonProperty("symbol") SymbolBusiness symbol) {
        super(name);
        this.color = color;
        this.symbol = symbol;
    }

    /* getters */

    @Override
    public SymbolInterface getSymbol() {
        return ((SymbolBusiness)symbol).clone();
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public ConnectFourPlayer clone() {
        ConnectFourPlayer clone = (ConnectFourPlayer) super.clone();
        if( clone != null) {
            clone.symbol = (symbol != null) ? ((SymbolBusiness) symbol).clone() : null;
            return clone;
        }
        return null;
    }
}
