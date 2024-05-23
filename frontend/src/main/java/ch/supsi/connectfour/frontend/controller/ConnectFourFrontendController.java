package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.view.BoardView;
import ch.supsi.connectfour.frontend.view.InfoBarView;
import ch.supsi.connectfour.frontend.view.SerializationView;
import ch.supsi.connectfour.frontend.view.Viewable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectFourFrontendController implements GameEventHandler {

    private static ConnectFourFrontendController instance;
    private final ConnectFourBackendController backendController = ConnectFourBackendController.getInstance();

    private ColumnsSelectorDispatcher columnsSelectorDispatcher;

    private final List<Viewable> viewableItems = new ArrayList<>();

    private final SerializationView serializationView = new SerializationView(MainFx.stage);
    private final TranslationsController translations = TranslationsController.getInstance();
    private static MenuItem saveMenu;


    public static ConnectFourFrontendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourFrontendController();
        }
        return instance;
    }

    private ConnectFourFrontendController() {
    }


    public ConnectFourFrontendController build(MenuItem saveMenuItem, ColumnsSelectorDispatcher btnDispatcher, Viewable... viewables) {
        this.columnsSelectorDispatcher = btnDispatcher;
        saveMenu = saveMenuItem;
        viewableItems.addAll(Arrays.stream(viewables).toList());
        return getInstance();
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
        GameEvent data = backendController.playerMove(column);

        data.handle(this);
        //System.out.println(backendController.getCurrentMatch());

    }

    /**
     * All serialization actions have some elements in common:
     * - Either enable or disable the saveMenu button
     * - Update the application title
     * - Clear all the views
     * It would be neat if we managed to generalize this behaviour and find a way to standardize it
     */

    public void manageNew() {
        if (this.backendController.getCurrentMatch() != null) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog(translations.translate("label.overwrite_confirmation"), translations.translate("label.confirmation"), translations.translate("label.confirm"), translations.translate("label.cancel"))) {
                // Update the current game stored in the controller, null indicates that it will create a new, blank instance inside the method
                this.backendController.overrideCurrentMatch(null);
                // Update the save button to prevent saving on new game
                saveMenu.setDisable(true);
                // TODO: FA SCHIFO!!!!!
                MainFx.stage.setTitle(MainFx.APP_TITLE);
                // Update the views
                this.clearViews();
                newGame();
            }
        } else {
            newGame();
        }
    }

    public void newGame() {
        this.clearViews();
        columnsSelectorDispatcher.disableButtons(false);
        backendController.createNewGame();
    }

    public void manageSave() {
        if (this.backendController.persist()) {
            this.serializationView.showMessage(translations.translate("label.correctly_saved"), null, Alert.AlertType.INFORMATION);
        } else {
            this.serializationView.showMessage(translations.translate("label.not_correctly_saved"), null, Alert.AlertType.ERROR);
        }
    }

    private void updateTitle(final @NotNull String gameName) {
        MainFx.stage.setTitle(MainFx.APP_TITLE + " - " + gameName.replaceAll(".json", ""));
    }

    public void manageSaveAs() {
        final File dir = this.serializationView.askForDirectory(new File(System.getProperty("user.home")), translations.translate("label.chosen_directory"));
        // Check if the dir variable points to something, wether the directory exists on the filesystem and is a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            final String fileName = this.serializationView.showInputDialog(translations.translate("label.insert_name"), translations.translate("label.insert_name_title"));

            if (fileName != null && this.backendController.persist(dir, fileName)) {
                this.serializationView.showMessage(translations.translate("label.correctly_saved"), null, Alert.AlertType.INFORMATION);
                saveMenu.setDisable(false);
                this.updateTitle(this.backendController.getSaveName());
            } else {
                this.serializationView.showMessage(translations.translate("label.not_correctly_saved"), null, Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void handle(MoveEvent event) {
        viewableItems.forEach(item -> item.show(event));
    }

    private void clearViews() {
        viewableItems.forEach(Viewable::clear);
    }

    /**
     * Scans the provided matrix column by column and updates the board view as long as it does not encounter any null references.
     * As soon as a row in a given column is null, it means there will not be any more tokens in that row, therefore this method
     * stops looping over that row and skips to the next column.
     *
     * @param newMatrix the matrix representing the board
     */
    private void updateBoard(final PlayerModel[][] newMatrix) {
        for (int column = newMatrix[0].length - 1; column >= 0; column--) {
            for (int row = newMatrix.length - 1; row >= 0; row--) {
                // As soon as it finds a null cell, it knows there can't be any more tokens so it skips to the next
                if (newMatrix[row][column] == null) {
                    break;
                }
                ValidMoveEvent move = new ValidMoveEvent(newMatrix[row][column], newMatrix[row][column], column, row);
                viewableItems.forEach(item -> item.show(move));
            }
        }
    }

    public void manageOpen() {
        final File file = this.serializationView.askForFile(translations.translate("label.select_file_to_load"));
        /*
        If it points to an instance of File, exists, is an actual file in the filesystem and can be read
         */
        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            final ConnectFourBusinessInterface loadedGame = this.backendController.tryLoadingSave(file);
            if (loadedGame != null) {

                this.clearViews();

                this.updateBoard(loadedGame.getGameMatrix());

                // Success!
                this.serializationView.showMessage(translations.translate("label.loading_confirmation"), translations.translate("label.confirm"), Alert.AlertType.INFORMATION);
                saveMenu.setDisable(false);

                this.updateTitle(loadedGame.getSaveName());
            } else {
                // Error while loading the game
                this.serializationView.showMessage(translations.translate("label.loading_error"), translations.translate("label.error"), Alert.AlertType.ERROR);
            }
        }
    }
}
