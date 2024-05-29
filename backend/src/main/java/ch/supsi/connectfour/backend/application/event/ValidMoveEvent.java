package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.backend.business.player.PlayerBusinessInterface;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ValidMoveEvent extends MoveEvent {

    private final int column;
    private final int row;
    private final ConnectFourPlayerInterface playerToPlay;
    private final SymbolInterface playerSymbol;
    private final String playerColor;

    public ValidMoveEvent(@NotNull ConnectFourPlayerInterface player, @NotNull ConnectFourPlayerInterface playerToPlay, int column, int row) {
        super(String.format(getTranslator().translate("label.player_moved"), player.getName(), playerToPlay.getName()),
                String.format(getTranslator().translate("label.player_moved_successfully"), player.getName(), row, column)
                , player);
        this.playerSymbol = player.getSymbol();
        this.playerColor = player.getColor();
        this.playerToPlay = playerToPlay;
        this.column = column;
        this.row = row;
    }

    protected ValidMoveEvent(String s, String logMessage,@NotNull ConnectFourPlayerInterface playerWhoWon, int column, int row) {
        super(s, logMessage, playerWhoWon);
        this.playerToPlay = null;
        this.playerSymbol = playerWhoWon.getSymbol();
        this.playerColor = playerWhoWon.getColor();
        this.column = column;
        this.row = row;
    }

    public SymbolInterface getPlayerSymbol() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidMoveEvent that)) return false;
        if (!super.equals(o)) return false;
        return getColumn() == that.getColumn() && getRow() == that.getRow() && Objects.equals(playerToPlay, that.playerToPlay) && getPlayerSymbol() == that.getPlayerSymbol() && Objects.equals(getPlayerColor(), that.getPlayerColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getColumn(), getRow(), playerToPlay, getPlayerSymbol(), getPlayerColor());
    }
}
