package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class InvalidMoveEvent extends MoveEvent{

    private final int column;

    public InvalidMoveEvent(PlayerModel player, PlayerModel playerToPlay, int column) {
        super(player.getName()+ " failed to move on column "+column, player, playerToPlay);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }
    @Override
    public void handle(GameEventHandler handler){
        handler.handle(this);
    }
}
