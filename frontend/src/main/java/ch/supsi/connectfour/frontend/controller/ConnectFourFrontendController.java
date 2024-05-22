package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.dispatcher.MenuBarDispatcher;
import ch.supsi.connectfour.frontend.view.BoardView;
import ch.supsi.connectfour.frontend.view.InfoBarView;
import ch.supsi.connectfour.frontend.view.SerializationView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

import java.io.File;

public class ConnectFourFrontendController implements GameEventHandler {

    private static ConnectFourFrontendController instance;
    private final ConnectFourBackendController backendController = ConnectFourBackendController.getInstance();

    private BoardView boardView;

    private InfoBarView infoBarView;

    private final SerializationView serializationView = new SerializationView(MainFx.stage);
    private final TranslationsBusinessInterface translations = TranslationsModel.getInstance();
    private static MenuItem saveMenu;


    public static ConnectFourFrontendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourFrontendController();
        }
        return instance;
    }
    private ConnectFourFrontendController() {}

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void setInfoBarView(InfoBarView infoBarView) {
        this.infoBarView = infoBarView;
    }

    public ConnectFourFrontendController build(BoardView boardView, InfoBarView infoBarView, MenuItem saveMenuItem) {
        if (infoBarView == null || boardView == null) {
            throw new IllegalArgumentException();
        }
        this.boardView = boardView;
        this.infoBarView = infoBarView;
        saveMenu = saveMenuItem;
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

        if (data == null) {
            infoBarView.setText(translations.translate("label.game_finished"));
        } else {
            data.handle(this);
        }
        System.out.println(backendController.getCurrentMatch());

    }

    public void manageNew() {
        if (this.backendController.getCurrentMatch() != null) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog(translations.translate("label.overwrite_confirmation"), translations.translate("label.confirmation"), translations.translate("label.confirm"), translations.translate("label.cancel"))) {
                // Update the current game stored in the controller, null indicates that it will create a new, blank instance inside the method
                this.backendController.overrideCurrentMatch(null);
                // Update the save button to prevent saving on new game
                saveMenu.setDisable(true);
                // Update the views
                this.clearViews();
            }
        }
    }

    public void manageSave() {
        // These two values are used to indicate that we want to use an already available file, stored in the current match
        this.backendController.persist(null, null);
    }

    private void updateTitle(final String gameName) {
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
            } else {
                this.serializationView.showMessage(translations.translate("label.not_correctly_saved"), null, Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void handle(WinEvent event) {
        boardView.setCellText(event.getRow(), event.getColumn(), event.getPlayerWhoWon().getName());
        infoBarView.setText(String.format("%s %s", event.getPlayerWhoWon().getName(), translations.translate("label.player_won")));
    }

    @Override
    public void handle(ValidMoveEvent event) {
        boardView.setCellText(event.getRow(), event.getColumn(), event.getPlayer().getName());
        infoBarView.setText(String.format("%s %s %s %s", event.getPlayer().getName(), translations.translate("label.player_moved"), event.getPlayerToPlay().getName(), translations.translate("label.player_turn")));
    }

    @Override
    public void handle(InvalidMoveEvent event) {
        infoBarView.setText(translations.translate("label.invalid_move"));
    }

    @Override
    public void handle(DrawEvent event) {
        infoBarView.setText(event.getEventMessage());
    }

    private void clearViews() {
        // TODO: it would be nice if we found a way to just add the views to a List of Viewable and simply do list.forEach(Viewable::clear);
        this.boardView.clear();
        this.infoBarView.clear();
    }

    private void updateBoard(final PlayerModel[][] newMatrix, final int[] lastOccupied) {

        // TODO: can be improved, if we already know the last index we can stop looping if not needed
        for (int row = 0; row < newMatrix.length; row++) {
            for (int column = 0; column < newMatrix[0].length; column++) {
                this.boardView.setCellText(row, column, newMatrix[row][column] == null ? "" : newMatrix[row][column].getName());
                // todo: implement logic to skip some computations
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

                //this.updateBoard(loadedGame.getGameMatrix(), loadedGame.getLastPositionOccupied());
                this.updateBoard(loadedGame.getGameMatrix(), null);

                // TODO: non sono sicuro di questa interazione. Il frontendcontroller puo' agire direttamente sul model? tecnicamente non è un layer sotto
                this.infoBarView.setText(this.backendController.getMessageToDisplay());

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
