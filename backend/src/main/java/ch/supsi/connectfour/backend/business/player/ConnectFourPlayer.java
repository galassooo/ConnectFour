package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectFourPlayer extends PlayerModel implements ConnectFourPlayerInterface {

    @JsonInclude
    private SymbolInterface symbol;

    @JsonInclude
    private String color;

    @JsonIgnore
    public ConnectFourPlayer(String name) {
        super(name);
    }

    @JsonIgnore
    public ConnectFourPlayer(String name, String color, SymbolInterface symbol) {
        super(name);
        this.color = color;
        this.symbol = symbol;
    }

    @JsonCreator
    private ConnectFourPlayer(@JsonProperty("name") String name,
                              @JsonProperty("color") String color,
                              @JsonProperty("symbol") Symbol symbol) {
        super(name);
        this.color = color;
        this.symbol = symbol;
    }

    public SymbolInterface getSymbol() {
        return symbol;
    }

    public void setSymbol(SymbolInterface symbol) {
        this.symbol = symbol;
    }

    public String getColor() {
        return color;
    }
}
