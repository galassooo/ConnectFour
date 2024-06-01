package ch.supsi.connectfour.backend.business.player;

import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

// When working with abstract-type fields,
// Jackson needs to know what the actual type of the object behind the field is
@JsonDeserialize(as = ConnectFourPlayer.class)
/*
 * Defines the behaviour that a generic ConnectFourPlayer should expose
 */
public interface ConnectFourPlayerInterface extends PlayerBusinessInterface {
    SymbolInterface getSymbol();

    void setSymbol(SymbolInterface symbol);

    String getColor();

    void setColor(String color);
}
