package ch.supsi.connectfour.backend.business.symbols;

import com.fasterxml.jackson.annotation.*;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SymbolBusiness implements SymbolInterface, Cloneable{
    /* fields */
    @JsonInclude
    private final String value;
    @JsonIgnore
    private final String name;

    /* constructors */
    @JsonCreator
    public SymbolBusiness(@NotNull @JsonProperty(value = "value") String value) {
        this.value = value;
        this.name = "";
    }

    @JsonIgnore
    public SymbolBusiness(@NotNull String value, String name) {
        this.value = value;
        this.name = name;
    }

    /* getters */
    @JsonIgnore
    @Override
    public URL getAsResource() {
        return getClass().getResource(value);
    }

    @JsonIgnore
    @Override
    public Character getAsChar() {
        return value.charAt(0);
    }

    @JsonIgnore
    @Override
    public InputStream getResourceStream() {
        return getClass().getResourceAsStream(value);
    }

    @JsonIgnore
    public String getValue() {
        return value;
    }

    @JsonIgnore
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
            return  (SymbolBusiness) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
