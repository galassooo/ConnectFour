package ch.supsi.connectfour.frontend.model.game;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.frontend.model.Translatable;

import java.io.File;

/**
 * Defines the behaviour that a generic game model should expose to the view and controller
 */
public interface IConnectFourModel extends Translatable {
    GameEvent playerMove(int column);

    boolean isCurrentMatchNull();

    void createNewGame();

    boolean persist();

    String getSaveName();

    boolean persist(File dir, String fileName);

    ConnectFourPlayerInterface getOtherPlayer(ConnectFourPlayerInterface player);

    ConnectFourBusinessInterface tryLoadingSave(File file);
}