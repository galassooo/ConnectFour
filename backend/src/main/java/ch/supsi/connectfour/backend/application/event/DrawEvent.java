package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class DrawEvent extends MoveEvent{

    public DrawEvent(PlayerModel player1, PlayerModel player2) {
        super("This game is a draw between "+player1+" and "+player2,player1, player2);
    }

    @Override
    public void handle(GameEventHandler handler){
        handler.handle(this);
    }
}
