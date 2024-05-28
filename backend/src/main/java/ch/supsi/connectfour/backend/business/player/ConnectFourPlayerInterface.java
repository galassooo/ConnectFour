package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;

public interface ConnectFourPlayerInterface extends PlayerBusinessInterface{
    SymbolInterface getSymbol();
    public String getColor();
}
