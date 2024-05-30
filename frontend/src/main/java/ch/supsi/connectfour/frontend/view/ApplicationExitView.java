package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.frontend.model.ApplicationExitModel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class ApplicationExitView implements IApplicationExitView {
    private static ApplicationExitModel model;

    public ApplicationExitView(ApplicationExitModel model) {
        ApplicationExitView.model = model;
    }

    public boolean showConfirmationDialog() {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(model.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(model.getMessage());

        // Set OK and Cancel buttons
        ButtonType okButton = new ButtonType(model.getConfirmText());
        ButtonType cancelButton = new ButtonType(model.getCancelText(), ButtonType.CANCEL.getButtonData());
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
