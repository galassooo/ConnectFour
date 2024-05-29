package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayer;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private final PreferencesBusinessInterface preferences;
    // The match currently linked to this controller
    private ConnectFourBusinessInterface currentMatch;

    // Singleton instantiation of this class
    public static ConnectFourBackendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourBackendController();
        }
        return instance;
    }

    protected ConnectFourBackendController() {
        preferences = PreferencesModel.getInstance();
    }

    /**
     * Serve per gestire la mossa del giocatore
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     * @return un oggetto contenente i dati relativi alla mossa, null se la partita è finita
     */
    public GameEvent playerMove(int column) {
        if (!currentMatch.isFinished()) {
            if (currentMatch.canInsert(column)) {
                currentMatch.insert(column);
                GameEvent data;

                ConnectFourPlayerInterface playerWhoMoved = currentMatch.getCurrentPlayer();
                currentMatch.switchCurrentPlayer();
                if (currentMatch.isWin()) {
                    currentMatch.setFinished(true);
                    data = new WinEvent(playerWhoMoved, column, currentMatch.getLastPositioned(column));
                } else {
                    data = new ValidMoveEvent(playerWhoMoved, currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column));
                }
                return data;
            } else if (currentMatch.isDraw()) {
                currentMatch.setFinished(true);
                return new DrawEvent(currentMatch.getPlayer1(), currentMatch.getPlayer2(), column);
            }
        }
        return new InvalidMoveEvent(currentMatch.getCurrentPlayer(), column);
    }

    public void createNewGame() {
        ConnectFourPlayerInterface p1 = new ConnectFourPlayer("P1", preferences.getPreference("player-one-color").toString(), preferences.getPreference("player-one-shape").toString());
        ConnectFourPlayerInterface p2 = new ConnectFourPlayer("P2", preferences.getPreference("player-two-color").toString(), preferences.getPreference("player-two-shape").toString());
        currentMatch = new ConnectFourModel(p1, p2);
    }

    public @Nullable ConnectFourBusinessInterface getCurrentMatch() {
        return currentMatch;
    }

    public void overrideCurrentMatch(@Nullable final ConnectFourBusinessInterface newMatch) {
        currentMatch = newMatch;
    }

    /**
     * Tries to persist the current match linked to this controller
     *
     * @param outputDirectory the directory where the game should be saved
     * @param saveName        the name for the save
     * @return true if the operation succeeded, false if it failed
     */
    public boolean persist(@Nullable final File outputDirectory, @Nullable final String saveName) {
        // TODO: fix this otherwise NPE
        if (currentMatch == null) {
            return false;
        }
        return currentMatch.persist(outputDirectory, saveName);
    }

    public boolean persist() {
        return currentMatch.persist();
    }

    /**
     * Tries loading the match stored in a file into this controller
     *
     * @param file a File instance representing the file in the filesystem
     * @return the deserialized game if the operation succeeded, null if it failed
     */
    public @Nullable ConnectFourBusinessInterface tryLoadingSave(@NotNull final File file) {
        // TODO: fix NPE if currentmatch is null
        if (currentMatch == null)
            this.createNewGame();

        final ConnectFourBusinessInterface loadedGame = currentMatch.getSave(file);
        // Override the current match if a match was successfully loaded & return it, else just return (null)
        if (loadedGame != null) {
            this.overrideCurrentMatch(loadedGame);
        }
        return loadedGame;
    }

    public String getSaveName() {
        return currentMatch.getSaveName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(preferences, getCurrentMatch());
    }
}
