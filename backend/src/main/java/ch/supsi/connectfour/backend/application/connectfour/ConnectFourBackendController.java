package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.application.serialization.SerializationBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private static PreferencesBusinessInterface preferences;
    private static SerializationBusinessInterface serialization;
    private static ConnectFourBusinessInterface currentMatch;


    //singleton pattern
    public static ConnectFourBackendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourBackendController();
        }
        return instance;
    }

    /**
     * Serve per gestire la mossa del giocatore
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     * @return un oggetto contenente i dati relativi alla mossa, null se la partita è finita
     */
    public @Nullable GameEvent playerMove(int column) {
        if (currentMatch == null) {
            createNewGame();
        }
        if (!currentMatch.isFinished()) {
            if (currentMatch.canInsert(column)) {
                currentMatch.insert(column);
                GameEvent data;

                PlayerModel playerWhoMoved = currentMatch.getCurrentPlayer();
                currentMatch.switchCurrentPlayer();
                if (currentMatch.checkWin()) {
                    currentMatch.setFinished(true);
                    playerWhoMoved.setNumWin(playerWhoMoved.getNumWin() + 1);
                    data = new WinEvent(playerWhoMoved, column, currentMatch.getLastPositioned(column));
                } else {
                    data = new ValidMoveEvent(playerWhoMoved, currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column));
                }
                return data;
            } else if (currentMatch.isDraw()) {
                currentMatch.setFinished(true);
                return new DrawEvent(currentMatch.getPlayer1(), currentMatch.getPlayer2());
            }
            return new InvalidMoveEvent(currentMatch.getCurrentPlayer(), currentMatch.getCurrentPlayer(), column);
        }
        return null;
    }

    public void createNewGame(){
        PlayerModel p1 = new PlayerModel("P1", getClass().getResource("/images/pawns/red.png"));
        PlayerModel p2 = new PlayerModel("P2", getClass().getResource("/images/pawns/yellow.png"));

        currentMatch = new ConnectFourModel(p1, p2);
    }
}
