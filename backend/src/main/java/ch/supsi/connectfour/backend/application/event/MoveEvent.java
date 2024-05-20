package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

//record per salvare i dati relativi alla mossa effettuata da un giocatore
public abstract class MoveEvent extends GameEvent {
    private final PlayerModel player;
    private final PlayerModel playerToPlay;

    public MoveEvent(String message, PlayerModel player, PlayerModel playerToPlay) {
        super(message);
        this.player = player;
        this.playerToPlay = playerToPlay;
    }

    public PlayerModel getPlayer() {
        return (PlayerModel) player.clone();
    }

    public PlayerModel getPlayerToPlay() {
        return (PlayerModel) playerToPlay.clone();
    }
}
