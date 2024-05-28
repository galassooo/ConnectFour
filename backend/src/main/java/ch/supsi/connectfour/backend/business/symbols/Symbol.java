package ch.supsi.connectfour.backend.business.symbols;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;

public class Symbol implements SymbolInterface{

    private final String value;

    public Symbol(@NotNull String value) {
        this.value = value;
    }

    public URL getAsResource(){
        return getClass().getResource(value);
    }

    public Character getAsChar(){
        return value.charAt(0);
    }

    public InputStream getResourceStream(){
        return getClass().getResourceAsStream(value);
    }
}
