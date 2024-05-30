package ch.supsi.connectfour.backend.business.symbols;

import com.fasterxml.jackson.annotation.*;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Symbol implements SymbolInterface{
    @JsonInclude
    private final String value;
    @JsonIgnore
    private final String name;
    @JsonCreator
    public Symbol(@NotNull @JsonProperty(value = "value") String value) {
        this.value = value;
        this.name = "";
    }
    @JsonIgnore
    public Symbol(@NotNull String value, String name) {
        this.value = value;
        this.name = name;
    }

    @JsonIgnore
    public URL getAsResource(){
        return getClass().getResource(value);
    }
    @JsonIgnore
    public Character getAsChar(){
        return value.charAt(0);
    }
    @JsonIgnore
    public InputStream getResourceStream(){
        return getClass().getResourceAsStream(value);
    }

    // TODO: non so se è legale per come l'abbiamo pensata. Il valore al suo interno rimarrà sempre una stringa in ogni caso, cambierebbe solo il modo in cui viene processata dall'esterno tramite i metodi sopra
    @JsonIgnore
    public String getValue() {
        return value;
    }
    @JsonIgnore
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
