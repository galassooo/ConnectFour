package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WinEvent extends ValidMoveEvent {

    private final PlayerModel playerWhoWon;

    public WinEvent(PlayerModel playerWhoWon, int column, int row) {
        super(String.format(getTranslator().translate("label.player_won"), playerWhoWon.getName()),
                String.format(getTranslator().translate("label.player_won_where"), playerWhoWon.getName(), column, row),
                playerWhoWon, column, row);
        this.playerWhoWon = playerWhoWon;
    }

    public PlayerModel getPlayerWhoWon() {
        return (PlayerModel) playerWhoWon.clone(); //safety copy
    }

    @Override
    public void handle(@NotNull GameEventHandler handler) {
        handler.handle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WinEvent winEvent)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getPlayerWhoWon(), winEvent.getPlayerWhoWon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayerWhoWon());
    }
}
