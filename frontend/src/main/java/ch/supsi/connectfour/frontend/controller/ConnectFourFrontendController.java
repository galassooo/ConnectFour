package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.MoveData;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
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
        MoveData data = backendController.playerMove(column);
        if (data != null && boardView != null && data.isValid()) { //allora la mossa è andata a buon fine
            boardView.setCellText(data.row(), data.column(), data.player().getName());

            if (infoBarView != null) {
                // TODO: QUESTI MESSAGGI SARANNO DA CARICARE TRAMITE LE TRADUZIONI
                if (data.win()) {
                    infoBarView.setText(data.player().getName() + " won the game!");

                } else {
                    infoBarView.setText(data.player().getName() + " moved, it's " + data.playerToPlay().getName() + "'s turn");
                }
            }
        }
        if (data == null) {
            infoBarView.setText("Match is finished! you can't move!");
        } else if (infoBarView != null && !data.isValid()) {
            infoBarView.setText("You cannot insert your pawn there!, try again");
        }
    }

    public void manageNew() {
        // TODO: find a way to check if a game has started. It doesn't make that much sense that the user is prompted with a confirmation request when no moves have been made yet
        if (this.backendController.getCurrentMatch() != null && !this.backendController.isCurrentGameFinished()) {
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
        //
        final File dir = this.serializationView.askForDirectory();
        final String fileName = this.serializationView.showInputDialog("Inserisci il nome da assegnare al file:");
        // Check if the dir variable points to something, wether the directory exists on the filesystem and is a directory
        if (dir != null && dir.exists() && dir.isDirectory() && fileName != null) {
            if (this.backendController.persist(dir, fileName)) {
                this.serializationView.showMessage("Yay salvato correttamente", null, Alert.AlertType.INFORMATION);
            } else {
                this.serializationView.showMessage(":(", null, Alert.AlertType.ERROR);
            }
        }
    }

    public void manageOpen() {
        final File file = this.serializationView.askForFile();
        if (file != null && file.exists() && file.isFile()) {
            final ConnectFourBusinessInterface loadedGame = this.backendController.tryLoadingSave(file);
            if (loadedGame != null) {
                // Success!
                System.out.println("YAY");
                // TODO: it would be nice if we found a way to just add the views to a List of Viewable and simply do list.forEach(Viewable::clear);
                this.boardView.clear();
                this.infoBarView.clear();

                final PlayerModel[][] gameMatrix = loadedGame.getGameMatrix();
                for (int row = 0; row < gameMatrix.length; row++) {
                    for (int column = 0; column < gameMatrix[0].length; column++) {
                        this.boardView.setCellText(row, column, gameMatrix[row][column] == null ? "" : gameMatrix[row][column].getName());
                    }
                }
            } else {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            }
        }
    }
}
