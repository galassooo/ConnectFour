package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public interface ConnectFourBusinessInterface {
    void switchCurrentPlayer();
    void insert(int column);
    boolean checkWin();
    boolean canInsert(int column);
    int getLastPositioned(int column);
    PlayerModel getCurrentPlayer();
    boolean isFinished();
    void setFinished(boolean finished);
    String getMessageToDisplay();
    void setCurrentPlayer(PlayerModel model);

    // Persistence related methods
    ConnectFourBusinessInterface getSave(final File file);
    boolean persist(final File outputDirectory, final String saveName);
    boolean wasSavedAs();
    PlayerModel[][] getGameMatrix();
    boolean isDraw();
    PlayerModel getPlayer1();
    PlayerModel getPlayer2();
}
