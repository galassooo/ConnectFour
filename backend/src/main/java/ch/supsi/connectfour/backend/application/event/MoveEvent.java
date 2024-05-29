package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

import java.util.Objects;

public abstract class MoveEvent extends GameEvent {
    private final ConnectFourPlayerInterface player;

    protected MoveEvent(String message, String logMessage, ConnectFourPlayerInterface player) {
        super(message, logMessage);
        this.player = player;
    }

    public ConnectFourPlayerInterface getPlayer() {
        return (ConnectFourPlayerInterface) player.clone();
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
