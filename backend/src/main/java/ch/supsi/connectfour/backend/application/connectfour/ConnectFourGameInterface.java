package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

/**
 * Defines a set of generic methods that a game model should provide to the controller
 */
public interface ConnectFourGameInterface {
    boolean canInsert(int column);

    void switchCurrentPlayer();

    void insert(int column);

    /* getter methods */
    int getLastPositioned(int column);

    ConnectFourPlayerInterface getCurrentPlayer();

    ConnectFourPlayerInterface getPlayer1();

    ConnectFourPlayerInterface getPlayer2();

    boolean isDraw();

    boolean isWin();

    ConnectFourPlayerInterface[][] getGameMatrix();

    boolean isFinished();

    /* setter methods */
    void setFinished(boolean finished);
}
