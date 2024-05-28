package ch.supsi.connectfour.backend.business.symbols;

import java.io.InputStream;
import java.net.URL;

public interface SymbolInterface {

    InputStream getResourceStream();
    Character getAsChar();
    URL getAsResource();
}
