package ch.supsi.connectfour.backend.business.player;

// Defines the behaviour that a generic player should expose
public interface PlayerBusinessInterface {

    String getName();

    int getId();

    Object clone();

}
