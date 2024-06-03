package ch.supsi.connectfour.frontend.controller.game;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.application.event.MoveEvent;
import ch.supsi.connectfour.backend.application.event.ValidMoveEvent;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.model.game.ConnectFourModel;
import ch.supsi.connectfour.frontend.model.game.IConnectFourModel;
import ch.supsi.connectfour.frontend.view.serialization.ISerializationView;
import ch.supsi.connectfour.frontend.view.serialization.SerializationView;
import ch.supsi.connectfour.frontend.view.viewables.InfoBarView;
import ch.supsi.connectfour.frontend.view.viewables.Viewable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectFourFrontendController implements GameEventHandler, IGameController {

    /* self reference */
    private static ConnectFourFrontendController instance;

    /* fxml items references */
    private static MenuItem saveMenu;
    private static Stage primaryStage;
    private final List<Button> buttonList = new ArrayList<>();

    /* models */
    private final IConnectFourModel model;

    /* views */
    private final ISerializationView serializationView;
    private final List<Viewable> viewableItems = new ArrayList<>();

    /**
     * Initialize models and serialization views
     */
    private ConnectFourFrontendController() {
        this.model = ConnectFourModel.getInstance();
        model.translateAndSave();

        this.serializationView = new SerializationView(primaryStage, model);
    }

    /**
     * @return the instance of this class
     */
    public static ConnectFourFrontendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourFrontendController();
        }
        return instance;
    }

    /**
     * Initialize the instance's fields
     *
     * @param saveMenuItem menuItem
     * @param buttonList   columns buttons
     * @param stage        primary stage (root)
     * @param viewables    components responsible for the display of game events
     */
    public void build(MenuItem saveMenuItem, List<Button> buttonList, Stage stage, Viewable... viewables) {
        this.buttonList.addAll(buttonList);
        primaryStage = stage;
        saveMenu = saveMenuItem;
        viewableItems.addAll(Arrays.stream(viewables).toList());
        InfoBarView.setDefaultMessage(model.getTranslation("label.infobar_welcome"));
    }

    /**
     * Handles the event triggered by a player selecting a column
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     */
    @Override
    public void manageColumnSelection(int column) {
        GameEvent data = model.playerMove(column);

        data.handle(this);
    }

    /**
     * Manage the player's request to create a new game
     */
    @Override
    public void manageNew() {
        if (!model.isCurrentMatchNull()) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog()) {
                // Update the save button to prevent saving on new game
                saveMenu.setDisable(true);
                primaryStage.setTitle(MainFx.APP_TITLE);
                newGame();
            }
        } else {
            // there is no game in progress, so it creates a new one
            newGame();
        }
    }

    /**
     * Enable columns buttons, ask model to create a new game and clear views
     */
    private void newGame() {
        this.clearViews();
        buttonList.forEach(btn -> btn.setDisable(false));
        model.createNewGame();
    }

    /**
     * Manage save request
     */
    @Override
    public void manageSave() {
        this.serializationView.showMessage(model.persist());
    }

    /**
     * Update the current stage title including the saving name
     *
     * @param gameName file name of the loaded match
     */
    private void updateTitle(final @NotNull String gameName) {
        primaryStage.setTitle(String.format("%s - %s", MainFx.APP_TITLE, gameName));
    }

    /**
     * Manage the save as request
     */
    @Override
    public void manageSaveAs() {
        final File dir = this.serializationView.askForDirectory(new File(System.getProperty("user.home")));

        // Check if the dir variable points to something, whether the directory exists on the filesystem and is a directory
        if (dir != null && dir.exists() && dir.isDirectory()) {

            final String fileName = this.serializationView.showInputDialog();

            if (fileName != null && !fileName.isEmpty() && model.persist(dir, fileName)) {

                this.serializationView.showMessage(true);

                saveMenu.setDisable(false);
                this.updateTitle(this.model.getSaveName());

            } else {
                this.serializationView.showMessage(false);
            }
        }
    }

    /**
     * handle the player move
     *
     * @param event the move event generated by the player
     */
    @Override
    public void handle(MoveEvent event) {
        viewableItems.forEach(item -> item.show(event));
    }

    /**
     * clear views
     */
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

                // As soon as it finds a null cell, it knows there can't be any more tokens it skips to the next
                if (newMatrix[row][column] == null) {
                    break;
                }
                ConnectFourPlayerInterface player = newMatrix[row][column];
                ValidMoveEvent move = new ValidMoveEvent(player, model.getOtherPlayer(player), column, row);
                viewableItems.forEach(item -> item.show(move));
            }
        }
    }

    /**
     * Handle open request
     */
    @Override
    public void manageOpen() {
        final File file = this.serializationView.askForFile();
        /*
        If it points to an instance of File, exists, is an actual file in the filesystem and can be read
         */
        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            final ConnectFourBusinessInterface loadedGame = model.tryLoadingSave(file);
            if (loadedGame != null) {

                this.clearViews();

                this.updateBoard(loadedGame.getGameMatrix());

                // Success!
                this.serializationView.showMessage(true);
                saveMenu.setDisable(false);

                this.updateTitle(loadedGame.getSaveName());
                if (!loadedGame.isFinished())
                    buttonList.forEach(btn -> btn.setDisable(false));
            } else {
                // Error while loading the game
                this.serializationView.showMessage(false);
            }
        }
    }
}
