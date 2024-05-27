package ch.supsi.connectfour.frontend;


import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.dispatcher.MenuBarDispatcher;
import ch.supsi.connectfour.frontend.view.viewables.BoardView;
import ch.supsi.connectfour.frontend.view.viewables.InfoBarView;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.ResourceBundle;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class MainFx extends Application {

    public static final String APP_TITLE = "ConnectFour";
    public static Stage stage;

    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();

    public MainFx() throws InstantiationException {
    }

    @Override
    public void start(Stage primaryStage) {
        // handle the main window close request
        // in real life, this event should not be dealt with here!
        // it should actually be delegated to a suitable ExitController!
        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    // consume the window event (the main window would be closed otherwise no matter what)
                    windowEvent.consume();

                    // hard close the primary stage
                    // javafx guarantees the clean exit of the javafx platform, when the last application stage is closed
                    primaryStage.close();
                }
        );
        primaryStage.setResizable(false);

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

            //TODO DA CAMBIAREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
            FXMLLoader columnSelectorsLoader = new FXMLLoader(fxmlUrl, ResourceBundle.getBundle("i18n/UI/ui_labels", Locale.forLanguageTag(String.valueOf(PreferencesController.getInstance().getPreference("language-tag"))), ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT)));
            columnSelectors = columnSelectorsLoader.load();
            columnsSelectorDispatcher = columnSelectorsLoader.getController();

        } catch (IOException e) {
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

        // PRIMARY STAGE
        primaryStage.setTitle(MainFx.APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/application/board.png"))));

        primaryStage.show();

        stage = primaryStage;
        connectFourFrontendController.build(menuBarDispatcher.saveMenuItem, columnsSelectorDispatcher.getButtons(), boardView, infoBarView);
    }

    @Override
    public void stop(){
        //Se ci sono processi extra, come Executors eccetera vanno chiusi qui !!!!PRIMA DEL PLATFORM EXIT!!!!
        Platform.exit();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
