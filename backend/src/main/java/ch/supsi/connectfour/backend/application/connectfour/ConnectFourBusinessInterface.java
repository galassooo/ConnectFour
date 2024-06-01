package ch.supsi.connectfour.backend.application.connectfour;

import java.io.File;

/**
 * Defines the methods that a generic connect four model should provide. All the methods in this interface are persistence
 * related. We chose to link the game-related methods with the persistence-related ones (through inheritance) because persistence in its current state
 * (it is not generic enough) cannot exist without a game.
 */
public interface ConnectFourBusinessInterface extends ConnectFourGameInterface {
    boolean persist(final File outputDirectory, final String saveName);

    boolean persist();

    /* Getter methods */
    ConnectFourBusinessInterface getSave(final File file);

    String getSaveName();

}
