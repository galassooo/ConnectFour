package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.SerializationFrontendController;
import javafx.event.ActionEvent;

public class MenuBarDispatcher {
    private final SerializationFrontendController serializationFrontendController = SerializationFrontendController.getInstance();

    public void newGame(ActionEvent actionEvent) {
        serializationFrontendController.manageNew();
    }

    public void openGame(ActionEvent actionEvent) {
        serializationFrontendController.manageOpen();
    }

    public void saveGame(ActionEvent actionEvent) {
        serializationFrontendController.manageSave();
    }

    public void saveGameAs(ActionEvent actionEvent) {
        serializationFrontendController.manageSaveAs();
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