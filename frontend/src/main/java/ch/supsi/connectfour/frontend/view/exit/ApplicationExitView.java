package ch.supsi.connectfour.frontend.view.exit;

import ch.supsi.connectfour.frontend.model.Translatable;
import ch.supsi.connectfour.frontend.model.exit.ApplicationExitModel;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class ApplicationExitView implements IApplicationExitView {
    /* model */
    private static Translatable model;

    /**
     * Construct the object
     *
     * @param model the associated model
     */
    public ApplicationExitView(ApplicationExitModel model) {
        ApplicationExitView.model = model;
    }

    /**
     * Builds and displays a confirmation dialog
     *
     * @return true if the user confirmed, false otherwise
     */
    @Override
    public boolean showConfirmationDialog() {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(model.getTranslation("label.confirmation"));
        alert.setHeaderText(null);
        alert.setContentText(model.getTranslation("label.close_confirmation"));

        // Set OK and Cancel button text
        ButtonType okButton = new ButtonType(model.getTranslation("label.confirm"));
        ButtonType cancelButton = new ButtonType(model.getTranslation("label.cancel"), ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().setAll(okButton, cancelButton);

        loadCssForAlert(alert);
        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // Return true if OK button is clicked, false otherwise
        return result.isPresent() && result.get() == okButton;
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
}
