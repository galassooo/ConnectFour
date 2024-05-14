package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private static PreferencesBusinessInterface preferences;
    // The match currently linked to this controller
    private static ConnectFourBusinessInterface currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));


    // Singleton instantiation of this class
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
    public @Nullable MoveData playerMove(int column) {
        if (currentMatch == null) {
            currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));
        }
        if (!currentMatch.isFinished()) {
            if (currentMatch.canInsert(column)) {
                currentMatch.insert(column);
                MoveData data;

                PlayerModel playerWhoMoved = currentMatch.getCurrentPlayer();
                currentMatch.switchCurrentPlayer();
                if (currentMatch.checkWin()) {
                    currentMatch.setFinished(true);
                    System.out.println("player ha vinto");
                    data = new MoveData(playerWhoMoved, currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column), true, true);
                } else {
                    data = new MoveData(playerWhoMoved, currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column), false, true);
                    System.out.println(currentMatch);
                    System.out.println("player ha mosso e il turno è cambiato");
                }
                return data;
            } else
                return new MoveData(currentMatch.getCurrentPlayer(), currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column), false, false);
        }
        return null;
    }
    public ConnectFourBusinessInterface getCurrentMatch() {
        return currentMatch;
    }

    /**
     * Overrides this instance's current match, if a new one is provided. If the provided match is null, it replaces it
     * with a new one.
     * This method is used with a null input parameter if a new game is requested, and with an actual instance of
     * ConnectFourModel if we are de-serializing a game and loading it.
     *
     * @param newMatch the match that should replace the current match, or null if a new, blank one is required
     */
    public void overrideCurrentMatch(@Nullable final ConnectFourBusinessInterface newMatch) {
        if (newMatch == null) {
            currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));
            return;
        }
        currentMatch = newMatch;
    }

    /**
     * Checks wether or not the current game is finished
     *
     * @return true if the game is finished, false if it is not
     */

    public boolean isCurrentGameFinished() {
        return currentMatch.isFinished();
    }

    /**
     * Tries to persist the current match linked to this controller
     *
     * @param outputDirectory the directory where the game should be saved
     * @param name the name for the save
     *
     * @return true if the operation succeeded, false if it failed
     */
    public boolean persist(@Nullable final File outputDirectory, @Nullable final String name) {
        return currentMatch.persist(outputDirectory, name);
    }

    /**
     * Tries loading the match stored in a file into this controller
     *
     * @param file a File instance representing the file in the filesystem
     * @return the deserialized game if the operation succeeded, null if it failed
     */
    public ConnectFourModel tryLoadingSave(@NotNull final File file) {
        final ConnectFourModel loadedGame = currentMatch.getSave(file);

        if (loadedGame != null) {
            this.overrideCurrentMatch(loadedGame);
        }
        return loadedGame;

    }
}
