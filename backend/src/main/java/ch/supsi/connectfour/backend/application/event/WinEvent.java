package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class WinEvent extends ValidMoveEvent{

    private final PlayerModel playerWhoWon;

    public WinEvent(PlayerModel playerWhoWon, int column, int row) {
        super(playerWhoWon.getName()+" won the game",playerWhoWon, column, row);
        this.playerWhoWon = playerWhoWon;
    }
    public PlayerModel getPlayerWhoWon() {
        return (PlayerModel) playerWhoWon.clone(); //safety copy
    }

    @Override
    public void handle(GameEventHandler handler){
        handler.handle(this);
    }
}
