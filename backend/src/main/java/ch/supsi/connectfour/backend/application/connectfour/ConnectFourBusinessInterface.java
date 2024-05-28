package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

import java.io.File;

public interface ConnectFourBusinessInterface extends ConnectFourGameInterface{
    ConnectFourBusinessInterface getSave(final File file);
    String getSaveName();

    /* Getter methods */
    ConnectFourPlayerInterface[][] getGameMatrix();

    boolean persist(final File outputDirectory, final String saveName);
    boolean persist();
}
