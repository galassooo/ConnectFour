package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.MoveData;
import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.view.BoardView;
import ch.supsi.connectfour.frontend.view.InfoBarView;
import ch.supsi.connectfour.frontend.view.SerializationView;
import javafx.scene.control.Alert;

import java.io.File;
// TODO: BACKUP THE INFOBAR

public class ConnectFourFrontendController {

    private static ConnectFourFrontendController instance;
    private final ConnectFourBackendController backendController = ConnectFourBackendController.getInstance();

    private BoardView boardView;

    private InfoBarView infoBarView;

    private final SerializationView serializationView = new SerializationView(MainFx.stage);
    private TranslationsBusinessInterface translations = TranslationsModel.getInstance();

    public static ConnectFourFrontendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourFrontendController();
        }
        return instance;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void setInfoBarView(InfoBarView infoBarView) {
        this.infoBarView = infoBarView;
    }

    /**
     * Effettua la chiamata al controller in backend per gestire la mossa e gestisce l'esito graficamente
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     */
    public void manageColumnSelection(int column) {
        // What information does this controller need?
        // - Wether or not the player moved successfully
        // - What message it should display
        MoveData data = backendController.playerMove(column);

        if (boardView != null && data.isValid()) {
            // Then it means the player was able to move successfully
            boardView.setCellText(data.row(), data.column(), data.player().getName());
        }
        infoBarView.setText(data.messageToDisplay());
    }

    public void manageNew() {
        // TODO: find a way to check if a game has started. It doesn't make that much sense that the user is prompted with a confirmation request when no moves have been made yet
        if (this.backendController.getCurrentMatch() != null) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog(
                    "Are you sure you want to start a new game? " +
                    "\n This will overwrite any ongoing games.")) {
                // Update the current game stored in the controller, null indicates that it will create a new, blank instance inside the method
                this.backendController.overrideCurrentMatch(null);

                // Update the views
                this.boardView.clear();
                this.infoBarView.clear();
            }
        }
    }

    public void manageSave() {
        // Ask the backend controller to ask the model if it was ever saved as
        if (this.backendController.wasCurrentGameSavedAs()) {
            // These two values are used to indicate that we want to use an already available file, stored in the current match
            this.backendController.persist(null, null);
        } else {
            // If the user has never saved this save as, then save as
            this.manageSaveAs();
        }
    }

    public void manageSaveAs() {
        // TODO: manage translations
        final File dir = this.serializationView.askForDirectory();
        final String fileName = this.serializationView.showInputDialog(translations.translate("insert_name"));
        // Check if the dir variable points to something, wether the directory exists on the filesystem and is a directory
        if (dir != null && dir.exists() && dir.isDirectory() && fileName != null) {
            if (this.backendController.persist(dir, fileName)) {
                this.serializationView.showMessage(translations.translate("correctly_saved"), null, Alert.AlertType.INFORMATION);
            } else {
                this.serializationView.showMessage(translations.translate("not_correctly_saved"), null, Alert.AlertType.ERROR);
            }
        }
    }

    public void manageOpen() {
        final File file = this.serializationView.askForFile();
        /*
        If it points to an instance of File, exists, is an actual file in the filesystem and can be read
         */
        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            final ConnectFourBusinessInterface loadedGame = this.backendController.tryLoadingSave(file);
            if (loadedGame != null) {
                // TODO: it would be nice if we found a way to just add the views to a List of Viewable and simply do list.forEach(Viewable::clear);
                this.boardView.clear();
                this.infoBarView.clear();

                // TODO: not sure if this is the best approach... should the frontend do this? I guess so, since it needs the view to perform this action...
                // Working on a deep copy of the actual game matrix to try and preserve invariants and prevent unauthorized modifications from the outside
                final PlayerModel[][] gameMatrix = loadedGame.getGameMatrix();
                // TODO: can be improved, if we already know the last index we can stop looping if not needed
                for (int row = 0; row < gameMatrix.length; row++) {
                    for (int column = 0; column < gameMatrix[0].length; column++) {
                        this.boardView.setCellText(row, column, gameMatrix[row][column] == null ? "" : gameMatrix[row][column].getName());
                    }
                }
                // TODO: non sono sicuro di questa interazione. Il frontendcontroller puo' agire direttamente sul model? tecnicamente non è un layer sotto
                this.infoBarView.setText(loadedGame.getMessageToDisplay());
                // Success!
                // TODO: remove these
                System.out.println(loadedGame);
                System.out.println("YAY");
                this.serializationView.showMessage("Salvataggio caricato correttamente", "conferma", Alert.AlertType.INFORMATION);
            } else {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        }
    }
}
