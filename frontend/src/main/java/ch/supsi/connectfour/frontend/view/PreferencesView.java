package ch.supsi.connectfour.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PreferencesView {
    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private ColorPicker playerOneColorPicker;

    @FXML
    private ColorPicker playerTwoColorPicker;

    @FXML
    private ComboBox<String> playerOneShapeComboBox;

    @FXML
    private ComboBox<String> playerTwoShapeComboBox;
    List<ComboBox<String>> playerShapeBoxes = new ArrayList<>();

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {
        // TODO: not sure if this is worth doing, especially considering that in this context, we will only ever have two players
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);
    }
    public void setShapes(List<String> supportedShapes) {
        playerShapeBoxes.forEach((cBox) -> {
            // Adds all supported shapes to the combo boxes and automatically selects the first option, to avoid having a blank cell
            cBox.getItems().addAll(supportedShapes);
            cBox.getSelectionModel().selectFirst();
        });
    }
    public void setLanguages(List<String> supportedLanguages) {
        // TODO: this displays the whole language tag in the format IT-CH, not sure if we want to process the tag further and only display the language (IT). That would have to be done in the frontend controller
        languageComboBox.getItems().setAll(supportedLanguages);
        languageComboBox.getSelectionModel().selectFirst();
    }


    public PreferencesView() {}

    public void setOnSaveButton(Consumer<ActionEvent> eventConsumer) {
        saveButton.setOnAction(eventConsumer::accept);
    }

    public void setOnCancelButton(Consumer<ActionEvent> eventConsumer) {
        cancelButton.setOnAction(eventConsumer::accept);
    }

    public String getSelectedLanguage() {
        return languageComboBox.getValue();
    }

    public String getPlayerOneColor() {
        return playerOneColorPicker.getValue().toString();
    }

    public String getPlayerTwoColor() {
        return playerTwoColorPicker.getValue().toString();
    }

    public String getPlayerOneShape() {
        return playerOneShapeComboBox.getValue();
    }

    public String getPlayerTwoShape() {
        return playerTwoShapeComboBox.getValue();
    }
}

