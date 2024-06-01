package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourBusiness;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayer;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.backend.business.preferences.PreferencesBusiness;
import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

//OK
public class ConnectFourApplication {

    /* self reference */
    private static ConnectFourApplication instance;

    /* business references */
    private final PreferencesBusinessInterface preferences;
    private ConnectFourBusinessInterface currentMatch; //match linked to controller


    protected ConnectFourApplication() {
        preferences = PreferencesBusiness.getInstance();
    }

    // Singleton instantiation of this class
    public static ConnectFourApplication getInstance() {
        if (instance == null) {
            instance = new ConnectFourApplication();
        }
        return instance;
    }

    /**
     * Handles the player's move
     *
     * @param column Column in which the player intends to insert the token
     * @return An event object containing the data related to the move, null if the game is over
     */
    public GameEvent playerMove(int column) {
        if (!currentMatch.isFinished()) {
            // se la partita è in corso
            if (currentMatch.canInsert(column)) { // e posso inserire la pedina
                currentMatch.insert(column); // allora la inserisco
                GameEvent data;

                ConnectFourPlayerInterface playerWhoMoved = currentMatch.getCurrentPlayer();
                currentMatch.switchCurrentPlayer(); // cambio il turno

                if (currentMatch.isWin()) { // se questa mossa è stata quella vincente

                    currentMatch.setFinished(true);  // allora avrò un win event
                    data = new WinEvent(playerWhoMoved, column, currentMatch.getLastPositioned(column));

                } else if (currentMatch.isDraw()) { // altrimenti se la mossa ha portato a uno stallo...
                    currentMatch.setFinished(true);
                    data = new DrawEvent(currentMatch.getPlayer1(), currentMatch.getPlayer2(), column);

                } else {
                    // altrimenti avrò semplicemente una mossa valida
                    data = new ValidMoveEvent(playerWhoMoved, currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column));
                }
                return data;
            }
        }
        // in caso non posso inserire allora avrò una mossa invalida
        return new InvalidMoveEvent(currentMatch.getCurrentPlayer(), column);
    }

    /**
     * Creates a new game
     */
    public void createNewGame() {
        SymbolInterface p1Symbol = new SymbolBusiness(preferences.getPreference("player-one-symbol").toString());
        SymbolInterface p2Symbol = new SymbolBusiness(preferences.getPreference("player-two-symbol").toString());
        ConnectFourPlayerInterface p1 = new ConnectFourPlayer("P1", preferences.getPreference("player-one-color").toString(), p1Symbol);
        ConnectFourPlayerInterface p2 = new ConnectFourPlayer("P2", preferences.getPreference("player-two-color").toString(), p2Symbol);
        currentMatch = new ConnectFourBusiness(p1, p2);
    }


    /**
     * Replaces the current game with the provided one
     *
     * @param newMatch new match
     */
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
        if (currentMatch == null) {
            return false;
        }
        return currentMatch.persist(outputDirectory, saveName);
    }

    /**
     * attempts to persist the current match
     *
     * @return true if the game was successfully saved, false otherwise
     */
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

        if (currentMatch == null)
            this.createNewGame();

        final ConnectFourBusinessInterface loadedGame = currentMatch.getSave(file);
        // Override the current match if a match was successfully loaded & return it, else just return (null)
        if (loadedGame != null) {
            this.overrideCurrentMatch(loadedGame);
        }
        return loadedGame;
    }

    /* getters */
    public String getSaveName() {
        return currentMatch.getSaveName();
    }

    public ConnectFourPlayerInterface getOtherPlayer(@NotNull ConnectFourPlayerInterface player) {
        if (player.equals(currentMatch.getPlayer1())) {
            return currentMatch.getPlayer2();
        }
        return currentMatch.getPlayer1();
    }

    public @Nullable ConnectFourBusinessInterface getCurrentMatch() {
        return currentMatch;
    }

    /* object override */
    @Override
    public int hashCode() {
        return Objects.hash(preferences, getCurrentMatch());
    }
}
