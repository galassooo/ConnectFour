package ch.supsi.connectfour.frontend.view.serialization;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;

public interface ISerializationView {
    File askForDirectory(File initialDirectory, String title, Stage stage);

    File askForFile(String title, Stage stage);

    void showMessage(String message, String title, Alert.AlertType type, Stage stage);

    boolean showConfirmationDialog(String message, String title, String confirmText, String cancelText, Stage stage);

    String showInputDialog(String message, String title);

}
