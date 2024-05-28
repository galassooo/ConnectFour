package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DrawEvent extends InvalidMoveEvent {
    private final PlayerModel otherPlayer;

    public DrawEvent(@NotNull PlayerModel player1, @NotNull PlayerModel player2, int col) {
        super(
                String.format(getTranslator().translate("label.player_draw"), player1.getName(), player2.getName()),
                String.format(getTranslator().translate("label.player_caused_draw"), player1.getName(), col),
                player1, col);
        otherPlayer = player2;
    }

    protected DrawEvent(String message, String logMessage, PlayerModel playerModel, PlayerModel playerModel2, int column) {
        super(message, logMessage, playerModel, column);
        otherPlayer = playerModel2;
    }

    @Override
    public void handle(@NotNull GameEventHandler handler) {
        handler.handle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawEvent drawEvent)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(otherPlayer, drawEvent.otherPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), otherPlayer);
    }
}
