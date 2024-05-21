package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class ValidMoveEvent extends MoveEvent{

    private final int column;
    private final int row;

    public ValidMoveEvent(PlayerModel player, PlayerModel playerToPlay, int column, int row) {
        super(player.getName()+" successfully inserted a pawn to cell "+row+"-"+column, player, playerToPlay);
        this.column = column;
        this.row = row;
    }

    public ValidMoveEvent(String s, PlayerModel playerWhoWon, int column, int row) {
        super(s, playerWhoWon, playerWhoWon);
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public void handle(GameEventHandler handler){
        handler.handle(this);
    }
}
