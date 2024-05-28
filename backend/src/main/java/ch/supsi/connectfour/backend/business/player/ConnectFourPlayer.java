package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;

public class ConnectFourPlayer extends PlayerModel implements ConnectFourPlayerInterface{

    private SymbolInterface symbol;
    private String color;
    public ConnectFourPlayer(String name) {
        super(name);
    }

    public ConnectFourPlayer(String name, String color, String symbolValue) {
        super(name);
        this.color = color;
        this.symbol = new Symbol(symbolValue);
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
