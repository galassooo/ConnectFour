package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

public interface ConnectFourGameInterface {
    int getLastPositioned(int column);

    boolean isDraw();

    boolean isWin();

    boolean canInsert(int column);

    boolean isFinished();

    void setFinished(boolean finished);

    void switchCurrentPlayer();

    void insert(int column);

    ConnectFourPlayerInterface getCurrentPlayer();

    ConnectFourPlayerInterface getPlayer1();

    ConnectFourPlayerInterface getPlayer2();
}
