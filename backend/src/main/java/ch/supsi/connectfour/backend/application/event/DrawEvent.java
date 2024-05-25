package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class DrawEvent extends InvalidMoveEvent {

    public DrawEvent(PlayerModel player1, PlayerModel player2, int col) {
        super(
                String.format(getTranslator().translate("label.player_draw"), player1.getName(), player2.getName()),
                String.format(getTranslator().translate("label.player_caused_draw"), player1.getName(), col),
                player1, col);

    }

    @Override
    public void handle(GameEventHandler handler) {
        handler.handle(this);
    }
}
