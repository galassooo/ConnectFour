package ch.supsi.connectfour.frontend;


import ch.supsi.connectfour.frontend.controller.ApplicationExitController;
import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.dispatcher.MenuBarDispatcher;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.viewables.BoardView;
import ch.supsi.connectfour.frontend.view.viewables.InfoBarView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainFx extends Application {

    public static final String APP_TITLE;
    static {
        APP_TITLE = TranslationModel.getInstance().translate("label.game_name");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
        final ApplicationExitController exitController = ApplicationExitController.getInstance();
        final TranslationModel translationsModel = TranslationModel.getInstance();
        exitController.build(primaryStage);

        primaryStage.setOnCloseRequest(event -> {
            event.consume(); //VA CHIAMATO PER GESTIRE L'EVENTO 'MANUALMENTE'
            exitController.manageExit();
        });
        primaryStage.setResizable(false);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/application/board.png"))));

        // MENU BAR
        MenuBar menuBar;
        MenuBarDispatcher menuBarDispatcher;
        try {
            URL fxmlUrl = getClass().getResource("/menubar.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader menuBarLoader = new FXMLLoader(fxmlUrl, translationsModel.getUiBundle());
            menuBar = menuBarLoader.load();
            menuBarDispatcher = menuBarLoader.getController();

        } catch (IOException e) {
            return;
        }

        // CONNECT-FOUR COLUMN SELECTORS
        Parent columnSelectors;
        ColumnsSelectorDispatcher columnsSelectorDispatcher;
        try {
            URL fxmlUrl = getClass().getResource("/columnselectors.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader columnSelectorsLoader = new FXMLLoader(fxmlUrl, translationsModel.getUiBundle());
            columnSelectors = columnSelectorsLoader.load();
            columnsSelectorDispatcher = columnSelectorsLoader.getController();

        } catch (IOException e) {
            return;
        }

        // CONNECT-FOUR BOARD
        Parent board;
        BoardView boardView;
        try {
            URL fxmlUrl = getClass().getResource("/gameboard.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader boardLoader = new FXMLLoader(fxmlUrl);
            board = boardLoader.load();
            boardView = boardLoader.getController();

        } catch (IOException e) {
            return;
        }

        // INFO BAR
        Parent infoBar;
        InfoBarView infoBarView;
        try {
            URL fxmlUrl = getClass().getResource("/infobar.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader infoBarLoader = new FXMLLoader(fxmlUrl);
            infoBar = infoBarLoader.load();
            infoBarView = infoBarLoader.getController();

        } catch (IOException e) {
            return;
        }

        // BORDER PANE
        BorderPane mainBorderPane = new BorderPane();

        mainBorderPane.setTop(menuBar);
        mainBorderPane.setStyle("-fx-background-color: #e3e3e3;");

        BorderPane gameBoardBorderPane = new BorderPane();
        gameBoardBorderPane.setTop(columnSelectors);
        gameBoardBorderPane.setCenter(board);
        mainBorderPane.setCenter(gameBoardBorderPane);

        mainBorderPane.setBottom(infoBar);

        // SCENE
        Scene scene = new Scene(mainBorderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        connectFourFrontendController.build(menuBarDispatcher.saveMenuItem, columnsSelectorDispatcher.getButtons(), primaryStage, boardView, infoBarView);
    }
}
