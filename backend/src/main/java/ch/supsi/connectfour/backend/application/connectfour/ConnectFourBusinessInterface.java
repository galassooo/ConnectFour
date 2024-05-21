package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface ConnectFourBusinessInterface {
    String getMessageToDisplay();
    int getLastPositioned(int column);
    ConnectFourBusinessInterface getSave(final File file);

    void switchCurrentPlayer();
    void insert(int column);
    void setFinished(boolean finished);
    void setCurrentPlayer(PlayerModel model);

    PlayerModel[][] getGameMatrix();
    PlayerModel getCurrentPlayer();
    PlayerModel getPlayer1();
    PlayerModel getPlayer2();

    boolean isDraw();
    boolean checkWin();
    boolean canInsert(int column);
    boolean isFinished();
    boolean persist(final File outputDirectory, final String saveName);
    boolean wasSavedAs();
}
