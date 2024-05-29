package ch.supsi.connectfour.backend.business.symbols;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.InputStream;
import java.net.URL;

@JsonDeserialize(as = Symbol.class)
public interface SymbolInterface {

    InputStream getResourceStream();
    Character getAsChar();
    URL getAsResource();
}
