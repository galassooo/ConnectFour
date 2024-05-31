package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;

import java.io.File;

/**
 * Defines the behaviour that a generic translations model should expose to the controller
 */
public interface ConnectFourDataAccessInterface {
    ConnectFourBusinessInterface getSave(final File file);

    String getFileExtension();
    boolean persist(final ConnectFourBusinessInterface game, final File outputFile);
}
