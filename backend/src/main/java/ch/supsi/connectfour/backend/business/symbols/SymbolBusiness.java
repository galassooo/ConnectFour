package ch.supsi.connectfour.backend.business.symbols;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;

public class SymbolBusiness implements SymbolInterface, Cloneable {
    /* fields */
    private final String value;
    private final String name;

    /* constructors */
    public SymbolBusiness(@NotNull String value) {
        this.value = value;
        this.name = "";
    }

    public SymbolBusiness(@NotNull String value, String name) {
        this.value = value;
        this.name = name;
    }

    /* getters */
    @Override
    public URL getAsResource() {
        return getClass().getResource(value);
    }

    @Override
    public Character getAsChar() {
        return value.charAt(0);
    }

    @Override
    public InputStream getResourceStream() {
        return getClass().getResourceAsStream(value);
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    /* overrides */

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public SymbolBusiness clone() {
        try {
            return (SymbolBusiness) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("An error occurred while trying to clone a Symbol object!");
            throw new AssertionError();
        }
    }
}
