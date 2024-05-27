package ch.supsi.connectfour.frontend.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class SerializationView {
    // TODO: probabilmente da sostituire, trovare un modo di passare il primary stage in giro
    private final Stage primaryStage;

    public SerializationView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showMessage(final String message, final String title, final Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/serialization/excl_mark.png"));

        loadCssForAlert(alert);
        alert.showAndWait();
    }
    private  void loadCssForAlert(@NotNull Alert alert){

        Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styleSheets/genericScreen.css")).toExternalForm());

        //System.out.println("AAAAAAAAAAA " + Objects.requireNonNull(getClass().getResource("/styleSheets/genericScreen.css")).toExternalForm());

        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
            button.getStyleClass().add("custom-button");
        });
    }

    public File askForDirectory(final File initialDirectory, final String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(initialDirectory);
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(primaryStage);
    }

    public File askForFile(final String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(primaryStage);
    }

    public boolean showConfirmationDialog(final String message, final String title, final String confirmText, final String cancelText) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Set OK and Cancel buttons
        ButtonType okButton = new ButtonType(confirmText);
        ButtonType cancelButton = new ButtonType(cancelText, ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        loadCssForAlert(alert);
        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if OK button is clicked, false otherwise
        return result.isPresent() && result.get() == okButton;
    }

    public String showInputDialog(final String message, final String title) {
        // Create a TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        // Show the input dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        // Return the user's input if "OK" is clicked, null otherwise
        return result.orElse(null);
    }
}
