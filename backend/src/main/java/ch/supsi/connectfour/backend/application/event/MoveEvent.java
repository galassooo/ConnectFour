package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

public abstract class MoveEvent extends GameEvent {
    private final PlayerModel player;

    protected MoveEvent(String message, String logMessage, PlayerModel player) {
        super(message, logMessage);
        this.player = player;
    }

    public PlayerModel getPlayer() {
        return (PlayerModel) player.clone();
    }
}
