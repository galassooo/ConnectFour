package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class InvalidMoveEvent extends MoveEvent {

    private final int column;

    public InvalidMoveEvent(PlayerModel player, int column) {
        super(getTranslator().translate("label.label.invalid_move"),
                String.format(getTranslator().translate("label.player_failed_move"), player.getName(), column)
                , player);
        this.column = column;
    }

    protected InvalidMoveEvent(String message, String logMessage, PlayerModel playerModel, int column) {
        super(message, logMessage, playerModel);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public void handle(GameEventHandler handler) {
        handler.handle(this);
    }
}
