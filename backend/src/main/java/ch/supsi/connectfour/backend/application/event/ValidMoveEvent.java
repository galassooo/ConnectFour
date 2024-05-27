package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.symbols.Symbol;
import org.jetbrains.annotations.NotNull;

public class ValidMoveEvent extends MoveEvent {

    private final int column;
    private final int row;
    private final PlayerModel playerToPlay;
    private Symbol playerSymbol;
    private String playerColor;

    public ValidMoveEvent(PlayerModel player, PlayerModel playerToPlay, int column, int row, Symbol symbol, String color) {
        super(String.format(getTranslator().translate("label.player_moved"), player.getName(), playerToPlay.getName()),
                String.format(getTranslator().translate("label.player_moved_successfully"), player.getName(), row, column)
                , player);
        this.playerSymbol = symbol;
        this.playerColor = color;
        this.playerToPlay = playerToPlay;
        this.column = column;
        this.row = row;
    }
    // TODO: simplify constr
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
    public void setPlayerSymbol(Symbol symbol) {
        this.playerSymbol = symbol;
    }
    public void setPlayerColor(String color) {
        this.playerColor = color;
    }

    public Symbol getPlayerSymbol() {
        return playerSymbol;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public void handle(@NotNull GameEventHandler handler) {
        handler.handle(this);
    }
}
