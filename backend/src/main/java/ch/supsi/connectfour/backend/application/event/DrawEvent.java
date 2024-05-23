package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class DrawEvent extends InvalidMoveEvent {

    public DrawEvent(PlayerModel player1, PlayerModel player2, int col) {
        super(
                getTranslator().translate("label.player_draw") + player1.getName() + " " + getTranslator().translate("label.and") + player2.getName(),
                player1.getName() + " caused a draw by inserting a pawn on col " + col,
                player1, col);
    }

    @Override
    public void handle(GameEventHandler handler) {
        handler.handle(this);
    }
}
