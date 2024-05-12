package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.controller.SerializationFrontendController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuBarDispatcher {
    private final SerializationFrontendController serializationFrontendController = SerializationFrontendController.getInstance();

    public void newGame(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Button button){
            serializationFrontendController.manageNew();
        }
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