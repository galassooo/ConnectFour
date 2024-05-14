package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import javafx.event.ActionEvent;

public class MenuBarDispatcher {
    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();

    public void newGame(ActionEvent actionEvent) {
        connectFourFrontendController.manageNew();
    }

    public void openGame(ActionEvent actionEvent) {
        connectFourFrontendController.manageOpen();
    }

    public void saveGame(ActionEvent actionEvent) {
        connectFourFrontendController.manageSave();
    }

    public void saveGameAs(ActionEvent actionEvent) {
        connectFourFrontendController.manageSaveAs();
    }

    public void quit(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }

    public void showAbout(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }

    public void showHelp(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }
}