package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private static PreferencesBusinessInterface preferences;
    private static TranslationsBusinessInterface translations;
    // The match currently linked to this controller
    private static ConnectFourBusinessInterface currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));

    // Singleton instantiation of this class
    public static ConnectFourBackendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourBackendController();
        }
        return instance;
    }
    private ConnectFourBackendController() {
        translations = TranslationsModel.getInstance();
    }

    /**
     * Serve per gestire la mossa del giocatore
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     * @return un oggetto contenente i dati relativi alla mossa, null se la partita è finita
     */
    public @NotNull MoveData playerMove(int column) {
        if (!currentMatch.isFinished()) {
            if (currentMatch.canInsert(column)) {
                currentMatch.insert(column);
                MoveData data;

                PlayerModel playerWhoMoved = currentMatch.getCurrentPlayer();
                if (currentMatch.checkWin()) {
                    currentMatch.setFinished(true);
                    System.out.println("player ha vinto");
                    data = new MoveData(playerWhoMoved, currentMatch.getLastPositioned(column), column, true, currentMatch.getMessageToDisplay());
                } else {
                    // Only switched if it actually makes sense to do so -> only if the game is still going
                    currentMatch.switchCurrentPlayer();
                    data = new MoveData(playerWhoMoved, currentMatch.getLastPositioned(column), column, true, currentMatch.getMessageToDisplay());
                    System.out.println(currentMatch);
                    System.out.println("player ha mosso e il turno è cambiato");
                }
                return data;
            }
        }
        // TODO: fa un po' schifo perché in realtà in questo caso non ho bisogno di player, row e column
        return new MoveData(null, -1, -1, false, currentMatch.getMessageToDisplay());
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
        // Check comment above for an explanation on why newMatch could ever be null and why
        if (newMatch == null) {
            currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));
            return;
        }
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
        return currentMatch.persist(outputDirectory, saveName);
    }

    public boolean wasCurrentGameSavedAs() {
        return currentMatch.wasSavedAs();
    }

    /**
     * Tries loading the match stored in a file into this controller
     *
     * @param file a File instance representing the file in the filesystem
     * @return the deserialized game if the operation succeeded, null if it failed
     */
    public @Nullable ConnectFourBusinessInterface tryLoadingSave(@NotNull final File file) {
        final ConnectFourBusinessInterface loadedGame = currentMatch.getSave(file);
        // Override the current match if a match was successfully loaded & return it, else just return (null)
        if (loadedGame != null) {
            this.overrideCurrentMatch(loadedGame);
        }
        return loadedGame;
    }
}
