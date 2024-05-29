package ch.supsi.connectfour.frontend;


import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import ch.supsi.connectfour.frontend.controller.*;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.dispatcher.MenuBarDispatcher;
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
import java.util.Locale;
import java.util.Objects;

public class MainFx extends Application {

    public static final String APP_TITLE = "ConnectFour";
    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    //private final ExitController exitController;
    // TODO: forse da cambiare...
    private final TranslationsModel translationsModel = TranslationsModel.getInstance();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/application/board.png"))));
        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    windowEvent.consume();
                    primaryStage.close();
                }
        );

        // MENU BAR
        MenuBar menuBar;
        MenuBarDispatcher menuBarDispatcher;
        try {
            URL fxmlUrl = getClass().getResource("/menubar.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader menuBarLoader = new FXMLLoader(fxmlUrl);
            menuBar = menuBarLoader.load();
            menuBarDispatcher = menuBarLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CONNECT-FOUR COLUMN SELECTORS
        Parent columnSelectors;
        ColumnsSelectorDispatcher columnsSelectorDispatcher;
        try {
            URL fxmlUrl = getClass().getResource("/columnselectors.fxml");
            if (fxmlUrl == null) {
                return;
            }

            //TODO DA CAMBIAREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE -> ORA VA BENEEEEE??????
            FXMLLoader columnSelectorsLoader = new FXMLLoader(fxmlUrl, translationsModel.getUIResourceBundle(Locale.forLanguageTag(String.valueOf(PreferencesController.getInstance().getPreference("language-tag")))));
            columnSelectors = columnSelectorsLoader.load();
            columnsSelectorDispatcher = columnSelectorsLoader.getController();

        } catch (IOException e) {
            // TODO: anche no
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }

        // INFO BAR
        Parent infoBar;
        InfoBarView infoBarView;
        try {
            URL fxmlUrl = getClass().getResource("/infobar.fxml");
            if (fxmlUrl == null) {
                // resource file not found
                return;
            }

            FXMLLoader infoBarLoader = new FXMLLoader(fxmlUrl);
            infoBar = infoBarLoader.load();
            infoBarView = infoBarLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
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
        connectFourFrontendController.build(menuBarDispatcher.saveMenuItem, columnsSelectorDispatcher.getButtons(),primaryStage, boardView, infoBarView);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
