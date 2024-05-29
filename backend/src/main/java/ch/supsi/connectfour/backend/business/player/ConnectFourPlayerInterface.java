package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ConnectFourPlayer.class)
public interface ConnectFourPlayerInterface extends PlayerBusinessInterface{
    SymbolInterface getSymbol();
    public String getColor();
}
