package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

public interface ConnectFourBusinessInterface {
    void switchCurrentPlayer();
    void insert(int column);
    boolean checkWin();
    boolean canInsert(int column);
    boolean setCurrentMatch(ConnectFourModel match);

    int getLastPositioned(int column);
    PlayerModel getCurrentPlayer();
    boolean isFinished();
    void setFinished(boolean finished);
}
