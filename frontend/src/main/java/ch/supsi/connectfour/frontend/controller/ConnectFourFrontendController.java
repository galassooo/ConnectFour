package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.application.event.MoveEvent;
import ch.supsi.connectfour.backend.application.event.ValidMoveEvent;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.model.ConnectFourModel;
import ch.supsi.connectfour.frontend.view.serialization.ISerializationView;
import ch.supsi.connectfour.frontend.view.serialization.SerializationView;
import ch.supsi.connectfour.frontend.view.viewables.InfoBarView;
import ch.supsi.connectfour.frontend.view.viewables.Viewable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectFourFrontendController implements GameEventHandler {

    private static ConnectFourFrontendController instance;
    private static MenuItem saveMenu;
    private static Stage primaryStage;

    private final ISerializationView serializationView;

    //DA CAMBIARE COL MODEL!!!!!
    private final TranslationsController translations;
    private final ConnectFourModel model;
    private final List<Viewable> viewableItems = new ArrayList<>();
    private final List<Button> buttonList = new ArrayList<>();

    private ConnectFourFrontendController() {
        this.model = ConnectFourModel.getInstance();
        this.serializationView = new SerializationView();
        this.translations = TranslationsController.getInstance();
    }

    public static ConnectFourFrontendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourFrontendController();
        }
        return instance;
    }

    public ConnectFourFrontendController build(MenuItem saveMenuItem, List<Button> buttonList, Stage stage, Viewable... viewables) {
        this.buttonList.addAll(buttonList);
        primaryStage = stage;
        saveMenu = saveMenuItem;
        viewableItems.addAll(Arrays.stream(viewables).toList());
        primaryStage = stage;
        InfoBarView.setDefaultMessage(this.translations.translate("label.infobar_welcome"));
        return getInstance();
    }

    /**
     * Effettua la chiamata al controller in backend per gestire la mossa e gestisce l'esito graficamente
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     */
    public void manageColumnSelection(int column) {
        GameEvent data = model.playerMove(column);

        data.handle(this);
    }

    public void manageNew() {
        if (!model.isCurrentMatchNull()) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog(translations.translate("label.overwrite_confirmation"), translations.translate("label.confirmation"), translations.translate("label.confirm"), translations.translate("label.cancel"), primaryStage)) {
                // Update the save button to prevent saving on new game
                saveMenu.setDisable(true);
                primaryStage.setTitle(MainFx.APP_TITLE);
                newGame();
            }
        } else {
            newGame();
        }
    }

    public void newGame() {
        this.clearViews();
        buttonList.forEach(btn -> btn.setDisable(false));
        model.createNewGame();
    }

    public void manageSave() {
        if (model.persist()) {
            this.serializationView.showMessage(translations.translate("label.correctly_saved"), null, Alert.AlertType.INFORMATION, primaryStage);
        } else {
            this.serializationView.showMessage(translations.translate("label.not_correctly_saved"), null, Alert.AlertType.ERROR, primaryStage);
        }
    }

    private void updateTitle(final @NotNull String gameName) {
        primaryStage.setTitle(String.format("%s - %s", MainFx.APP_TITLE, gameName));
    }

    public void manageSaveAs() {
        final File dir = this.serializationView.askForDirectory(new File(System.getProperty("user.home")), translations.translate("label.chosen_directory"), primaryStage);
        // Check if the dir variable points to something, whether the directory exists on the filesystem and is a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {
            final String fileName = this.serializationView.showInputDialog(translations.translate("label.insert_name"), translations.translate("label.insert_name_title"));

            if (fileName != null && model.persist(dir, fileName)) {
                this.serializationView.showMessage(translations.translate("label.correctly_saved"), null, Alert.AlertType.INFORMATION, primaryStage);
                saveMenu.setDisable(false);
                this.updateTitle(this.model.getSaveName());
            } else {
                this.serializationView.showMessage(translations.translate("label.not_correctly_saved"), null, Alert.AlertType.ERROR, primaryStage);
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
    private void updateBoard(@NotNull final ConnectFourPlayerInterface[] @NotNull [] newMatrix) {
        for (int column = newMatrix[0].length - 1; column >= 0; column--) {
            for (int row = newMatrix.length - 1; row >= 0; row--) {
                // As soon as it finds a null cell, it knows there can't be any more tokens so it skips to the next
                if (newMatrix[row][column] == null) {
                    break;
                }
                ConnectFourPlayerInterface player = newMatrix[row][column];
                ValidMoveEvent move = new ValidMoveEvent(player, model.getOtherPlayer(player), column, row);
                viewableItems.forEach(item -> item.show(move));
            }
        }
    }

    public void manageOpen() {
        final File file = this.serializationView.askForFile(translations.translate("label.select_file_to_load"), primaryStage);
        /*
        If it points to an instance of File, exists, is an actual file in the filesystem and can be read
         */
        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            final ConnectFourBusinessInterface loadedGame = model.tryLoadingSave(file);
            if (loadedGame != null) {

                this.clearViews();

                this.updateBoard(loadedGame.getGameMatrix());

                // Success!
                this.serializationView.showMessage(translations.translate("label.loading_confirmation"), translations.translate("label.confirm"), Alert.AlertType.INFORMATION, primaryStage);
                saveMenu.setDisable(false);

                this.updateTitle(loadedGame.getSaveName());
                if (!loadedGame.isFinished())
                    buttonList.forEach(btn -> btn.setDisable(false));
            } else {
                // Error while loading the game
                this.serializationView.showMessage(translations.translate("label.loading_error"), translations.translate("label.error"), Alert.AlertType.ERROR, primaryStage);
            }
        }
    }
}
