package ch.supsi.connectfour.frontend.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class SerializationView {
    // TODO: probabilmente da sostituire, trovare un modo di passare il primary stage in giro
    private final Stage primaryStage;

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
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        directoryChooser.setTitle("Scegli la directory dove salvare il file...");
        return directoryChooser.showDialog(primaryStage);
    }
    public File askForFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona il file da cui caricare la partita...");
        return fileChooser.showOpenDialog(primaryStage);
    }
    public boolean showConfirmationDialog(String message) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // TODO: handle with translations
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Set OK and Cancel buttons
        // TODO: handle with translations
        ButtonType okButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if OK button is clicked, false otherwise
        return result.isPresent() && result.get() == okButton;
    }
    public String showInputDialog(String message) {
        // Create a TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        // Show the input dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        // Return the user's input if "OK" is clicked, null otherwise
        return result.orElse(null);
    }
}
