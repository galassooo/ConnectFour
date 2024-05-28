package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InvalidMoveEvent extends MoveEvent {

    private final int column;

    public InvalidMoveEvent(PlayerModel player, int column) {
        super(getTranslator().translate("label.invalid_move"),
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
    public void handle(@NotNull GameEventHandler handler) {
        handler.handle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvalidMoveEvent that)) return false;
        if (!super.equals(o)) return false;
        return getColumn() == that.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getColumn());
    }
}
