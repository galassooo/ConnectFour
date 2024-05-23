package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class ValidMoveEvent extends MoveEvent {

    private final int column;
    private final int row;
    private final PlayerModel playerToPlay;

    public ValidMoveEvent(PlayerModel player, PlayerModel playerToPlay, int column, int row) {
        super(String.format(getTranslator().translate("label.player_moved"), player.getName(), playerToPlay.getName()),
                String.format(getTranslator().translate("label.player_moved_successfully"), player.getName(), row, column)
                , player);
        this.playerToPlay = playerToPlay;
        this.column = column;
        this.row = row;
    }

    protected ValidMoveEvent(String s, String logMessage, PlayerModel playerWhoWon, int column, int row) {
        super(s, logMessage, playerWhoWon);
        this.playerToPlay = null;
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
    public void handle(GameEventHandler handler) {
        handler.handle(this);
    }
}
