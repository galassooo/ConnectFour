package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.application.serialization.SerializationController;
import ch.supsi.connectfour.frontend.MainFx;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SerializationView {
    private Stage primaryStage;

    public SerializationView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showMessage(String message, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // TODO: LOAD THESE MESSAGES THROUGH TRANSLATIONS
    public File askForDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Scegli la directory dove salvare il file...");
        return directoryChooser.showDialog(primaryStage);
    }
    public File askForFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona il file da cui caricare la partita...");
        return fileChooser.showOpenDialog(primaryStage);
    }
}
