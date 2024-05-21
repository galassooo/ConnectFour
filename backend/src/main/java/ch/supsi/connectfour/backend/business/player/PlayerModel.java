package ch.supsi.connectfour.backend.business.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public final class PlayerModel implements Cloneable{
    // TODO: FOR TESTING PURPOSES
    @JsonIgnore
    public int id;
    private static int ids = 0;
    @JsonInclude
    private String name;
    @JsonInclude
    private int numWin;

    //TODO DA MODIFICARE! SERVIVA PER TESTARE E NON CE ANCORA IL CARICAMENTO DELLE PREFERENZE
    @JsonInclude
    private URL preferenceUrl;

    @JsonCreator
    public PlayerModel(@JsonProperty(value = "name") String name, @JsonProperty(value = "numWin") int numWin , @JsonProperty(value = "preferenceUrl") URL preferenceUrl){
        this.id = ids + 1;
        ids++;
        this.name = name;
        this.numWin = numWin;
        this.preferenceUrl = preferenceUrl;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null || name.isEmpty() || name.isBlank() ? "not available" : name;
    }

    public int getNumWin() {
        return numWin;
    }

    public void setNumWin(int numWin) {
        this.numWin =Math.max(0, numWin);
    }

    public void setPreferenceUrl(URL preferenceUrl) {
        if(preferenceUrl == null){
            throw new IllegalArgumentException("Url cannot be null");
        }
        this.preferenceUrl = preferenceUrl;
    }

    public URL getPreferenceUrl() {
        return preferenceUrl;
    }

    @Override
    public Object clone(){
        try {
             PlayerModel cloned = (PlayerModel) super.clone();
             cloned.preferenceUrl = new URL(this.preferenceUrl.toString());
             return cloned;
        } catch (CloneNotSupportedException e) {
            System.err.println("Clone function is not supported in a Player's superclass");
        } catch (MalformedURLException e) {
            System.err.println("Malformed url");
        }
        return null;
    }
    //solo per test
    @Override
    public String toString() {
        return "name: " + name  +
                ", win: " + numWin;
    }

    //todo: considerare un modo per rendere univoco ogni player
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerModel player)) return false;
        return getNumWin() == player.getNumWin() && Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumWin());
    }
}
