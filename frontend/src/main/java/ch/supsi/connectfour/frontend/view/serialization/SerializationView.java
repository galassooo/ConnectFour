package ch.supsi.connectfour.frontend.view.serialization;

import ch.supsi.connectfour.frontend.model.game.IConnectFourModel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class SerializationView implements ISerializationView {
    private final Stage root;
    private final IConnectFourModel model;

    public SerializationView(Stage stage, IConnectFourModel model) {
        this.root = stage;
        this.model = model;
    }

    /**
     * Builds a message to display to the user with the given message, popup title and type of alert
     */
    @Override
    public void showMessage(boolean success) {
        Alert alert = success ? new Alert(Alert.AlertType.INFORMATION) : new Alert(Alert.AlertType.ERROR);
        alert.initOwner(root);
        alert.setHeaderText(null);
        if (success) {
            alert.setTitle(model.getSuccess());
            alert.setContentText(model.getCorrectlySaved());
        } else {
            alert.setTitle(model.getError());
            alert.setContentText(model.getNotCorrectlySaved());
        }
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/serialization/excl_mark.png"));

        loadCssForAlert(alert);
        alert.showAndWait();
    }

    /**
     * Loads a CSS stylesheet for the popup
     *
     * @param alert alert popup
     */
    private void loadCssForAlert(@NotNull Alert alert) {
        //retrieve the scene
        Scene scene = alert.getDialogPane().getScene();

        //load stylesheet
        String style = Objects.requireNonNull(getClass().getResource("/styleSheets/genericScreen.css")).toExternalForm();

        //add stylesheet to the scene styles
        scene.getStylesheets().add(style);

        //set the custom-alert (declared in the css file)  values to alert
        alert.getDialogPane().getStyleClass().add("custom-alert");

        //set the custom-button (declared in the css file) values to all buttons
        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
            button.getStyleClass().add("custom-button");
        });
    }

    /**
     * Builds and displays a window to ask the user for a directory on their filesystem
     *
     * @param initialDirectory the initial directory where the directoryChooser will be
     * @return a File instance representing the Directory the user selected through the DirectoryChooser
     */
    @Override
    public File askForDirectory(final File initialDirectory) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(initialDirectory);
        directoryChooser.setTitle(model.getChooseDirectory());
        return directoryChooser.showDialog(root);
    }

    /**
     * Builds and displays a window to ask the user for a file  on their filesystem
     *
     * @return a File instance representing the Directory the user selected through the DirectoryChooser
     */
    @Override
    public File askForFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(model.getInsertNameTitle());
        return fileChooser.showOpenDialog(root);
    }

    /**
     * Builds and shows a confirmation dialog to the user
     */
    @Override
    public boolean showConfirmationDialog() {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(root);
        alert.setTitle(model.getConfirmation());
        alert.setHeaderText(null);
        alert.setContentText(model.getOverWrite());

        // Set OK and Cancel buttons
        ButtonType okButton = new ButtonType(model.getConfirm());
        ButtonType cancelButton = new ButtonType(model.getCancel(), ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        loadCssForAlert(alert);
        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if OK button is clicked, false otherwise
        return result.isPresent() && result.get() == okButton;
    }

    /**
     * Builds and shows a text input dialog
     */
    @Override
    public String showInputDialog() {
        // Create a TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(model.getInsertNameTitle());
        dialog.setHeaderText(null);
        dialog.setContentText(model.getInsertName());

        // Show the input dialog and wait for user input
        Optional<String> result = dialog.showAndWait();

        // Return the user's input if "OK" is clicked, null otherwise
        return result.orElse(null);
    }
}
