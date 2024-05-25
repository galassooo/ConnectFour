package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class GameFinishedEvent extends InvalidMoveEvent {
    public GameFinishedEvent(PlayerModel player, int column) {
        super(String.format(getTranslator().translate("label.game_finished")),
                String.format(getTranslator().translate("label.player_tried_to_move"), player.getName(), column),
                player, column);
    }
}
