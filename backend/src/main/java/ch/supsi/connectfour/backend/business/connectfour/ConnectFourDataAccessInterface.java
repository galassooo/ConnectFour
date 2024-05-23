package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;

import java.io.File;

public interface ConnectFourDataAccessInterface {
    ConnectFourBusinessInterface getSave(final File file);
    boolean persist(final ConnectFourBusinessInterface game, final File outputFile);
}
