package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.business.player.PlayerModel;

//record per salvare i dati relativi alla mossa effettuata da un giocatore
public record MoveData(PlayerModel player, PlayerModel playerToPlay, int column, int row, boolean win, boolean isValid) {

}
