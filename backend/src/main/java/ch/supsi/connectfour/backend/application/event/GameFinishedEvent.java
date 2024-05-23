package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

public class GameFinishedEvent extends InvalidMoveEvent {
    public GameFinishedEvent(PlayerModel player, int column) {
        super(getTranslator().translate("label.game_finished"), player.getName() + " attempted to move in  column " + column + " after the match was finished", player, column);
    }

}
