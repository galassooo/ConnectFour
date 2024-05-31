package ch.supsi.connectfour.frontend.model.game;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

import java.io.File;

public interface IConnectFourModel {
    GameEvent playerMove(int column);

    boolean isCurrentMatchNull();

    void createNewGame();

    boolean persist();

    String getSaveName();

    boolean persist(File dir, String fileName);

    ConnectFourPlayerInterface getOtherPlayer(ConnectFourPlayerInterface player);

    ConnectFourBusinessInterface tryLoadingSave(File file);
}
