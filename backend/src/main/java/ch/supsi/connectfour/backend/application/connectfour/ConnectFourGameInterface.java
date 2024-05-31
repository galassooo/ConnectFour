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
    void setCurrentPlayer(ConnectFourPlayerInterface player);

    ConnectFourPlayerInterface getPlayer1();
    void setPlayer1(ConnectFourPlayerInterface player1);

    ConnectFourPlayerInterface getPlayer2();
    void setPlayer2(ConnectFourPlayerInterface player2);
}
