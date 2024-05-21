package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.*;
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
    private static ConnectFourBusinessInterface currentMatch;

    // Singleton instantiation of this class
    public static ConnectFourBackendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourBackendController();
        }
        return instance;
    }

    private ConnectFourBackendController() {
        createNewGame();
        translations = TranslationsModel.getInstance();
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

    public ConnectFourBusinessInterface getCurrentMatch() {
        return currentMatch;
    }

    public void overrideCurrentMatch(@Nullable final ConnectFourBusinessInterface newMatch) {
        // Check comment above for an explanation on why newMatch could ever be null and why
        if (newMatch == null) {
            createNewGame();
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

    public String getMessageToDisplay() {
        /*
         * Four possible cases:
         * - Player moved, game isn't finished
         * - Player moved, they won
         * - Player tried to move but game is finished
         * - Player tried to move but the move isn't valid
         */

        final PlayerModel player1 = currentMatch.getPlayer1();
        final PlayerModel player2 = currentMatch.getPlayer2();
        final PlayerModel currentPlayer = currentMatch.getCurrentPlayer();

        if (currentMatch.isLastMoveValid() && !currentMatch.isFinished()) {
            return String.format("%s  %s %s %s",
                    (currentPlayer.equals(player2) ? player1.getName() : player2.getName()),
                    translations.translate("label.player_moved"),
                    currentPlayer.getName(),
                    translations.translate("label.player_turn"));
        } else if (currentMatch.isLastMoveValid()) {
            // If we are here then the game must be finished
            return String.format("%s %s", currentPlayer.getName(), translations.translate("label.player_won"));
        } else if (currentMatch.isFinished()) {
            // If we are here then the last move wasn't valid
            return translations.translate("label.game_finished");
        } else {
            // If we are here then the move wasn't valid AND the game is not finished
            return translations.translate("label.invalid_move");
        }
    }

    public void createNewGame() {
        PlayerModel p1 = new PlayerModel("P1", 0);
        PlayerModel p2 = new PlayerModel("P2", 0);

        currentMatch = new ConnectFourModel(p1, p2);
    }

    public String getSaveName() {
        return currentMatch.getSaveName();
    }
}
