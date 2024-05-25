package ch.supsi.connectfour.backend.business.symbols;

import java.net.URL;

public enum Symbol {
    STAR(Symbol.class.getResource("/images/symbols/star.PNG")),
    HEART(Symbol.class.getResource("/images/symbols/heart.PNG")),
    CIRCLE(Symbol.class.getResource("/images/symbols/circle.PNG")),
    SQUARE(Symbol.class.getResource("/images/symbols/square.PNG"));

    final URL resource;

    Symbol(URL resourceURL) {
        resource = resourceURL;
    }

    public URL getResource() {
        return resource;
    }
}
