package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameFinishedEvent extends InvalidMoveEvent {
    //TODO aggiungere un campo altrimenti eliminare la classe
    public GameFinishedEvent(@NotNull PlayerModel player, int column) {
        super(String.format(getTranslator().translate("label.game_finished")),
                String.format(getTranslator().translate("label.player_tried_to_move"), player.getName(), column),
                player, column);
    }
    protected GameFinishedEvent(String message, String logMessage, @NotNull PlayerModel playerModel, int column) {
        super(message, logMessage, playerModel, column);
    }

    @Override
    public void handle(@NotNull GameEventHandler handler) {
        handler.handle(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
