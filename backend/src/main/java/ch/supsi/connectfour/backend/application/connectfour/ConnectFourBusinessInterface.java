package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

import java.io.File;

public interface ConnectFourBusinessInterface {
    int getLastPositioned(int column);
    ConnectFourBusinessInterface getSave(final File file);
    String getSaveName();

    void switchCurrentPlayer();
    void insert(int column);
    void setFinished(boolean finished);
    /* Getter methods */
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
    boolean isLastMoveValid();
}
