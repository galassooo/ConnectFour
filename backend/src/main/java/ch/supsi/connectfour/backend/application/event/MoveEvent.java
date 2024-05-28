package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

import java.util.Objects;

public abstract class MoveEvent extends GameEvent {
    private final PlayerModel player;

    protected MoveEvent(String message, String logMessage, PlayerModel player) {
        super(message, logMessage);
        this.player = player;
    }

    public PlayerModel getPlayer() {
        return (PlayerModel) player.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveEvent moveEvent)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getPlayer(), moveEvent.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayer());
    }
}
