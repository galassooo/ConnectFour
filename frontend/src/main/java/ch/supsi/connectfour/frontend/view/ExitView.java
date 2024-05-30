package ch.supsi.connectfour.frontend.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class ExitView {

    public boolean showConfirmationDialog(final String message, final String title, final String confirmText, final String cancelText, Stage stage) {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
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

    private void loadCssForAlert(@NotNull Alert alert) {

        Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styleSheets/genericScreen.css")).toExternalForm());

        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.getDialogPane().getButtonTypes().forEach(buttonType -> {
            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
            button.getStyleClass().add("custom-button");
        });
    }
}
