package ch.supsi.connectfour.frontend;


import ch.supsi.connectfour.frontend.controller.GameController;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.dispatcher.MenuBarDispatcher;
import ch.supsi.connectfour.frontend.view.BoardView;
import ch.supsi.connectfour.frontend.view.InfoBarView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainFx extends Application {

    public static final String APP_TITLE = "connectfour";

    private MenuBarDispatcher menuBarDispatcher;
    private ColumnsSelectorDispatcher columnsSelectorDispatcher;
    private BoardView boardView;
    private InfoBarView infoBarView;
    private GameController gameController = GameController.getInstance();
    private static MainFx instance;

    //singleton
    public static MainFx getInstance() {
        return instance;
    }

    public MainFx() throws InstantiationException {
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
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
        try {
            URL fxmlUrl = getClass().getResource("/menubar.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader menuBarLoader = new FXMLLoader(fxmlUrl);
            menuBar = menuBarLoader.load();
            this.menuBarDispatcher = menuBarLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CONNECT-FOUR COLUMN SELECTORS
        Parent columnSelectors;
        try {
            URL fxmlUrl = getClass().getResource("/columnselectors.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader columnSelectorsLoader = new FXMLLoader(fxmlUrl);
            columnSelectors = columnSelectorsLoader.load();
            this.columnsSelectorDispatcher = columnSelectorsLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // CONNECT-FOUR BOARD
        Parent board;
        try {
            URL fxmlUrl = getClass().getResource("/gameboard.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader boardLoader = new FXMLLoader(fxmlUrl);
            board = boardLoader.load();
            this.boardView = boardLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // INFO BAR
        Parent infoBar;
        try {
            URL fxmlUrl = getClass().getResource("/infobar.fxml");
            if (fxmlUrl == null) {
                // resource file not found
                return;
            }

            FXMLLoader infoBarLoader = new FXMLLoader(fxmlUrl);
            infoBar = infoBarLoader.load();
            this.infoBarView = infoBarLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // BORDER PANE
        BorderPane mainBorderPane = new BorderPane();

        mainBorderPane.setTop(menuBar);

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
        primaryStage.show();
        gameController.build(this.boardView, this.infoBarView);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
