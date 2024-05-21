package ch.supsi.connectfour.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

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

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void initialize() {
        // Initialize combo boxes with sample data
        languageComboBox.getItems().addAll("English", "Italian");
        playerOneShapeComboBox.getItems().addAll("Circle", "Rectangle");
        playerTwoShapeComboBox.getItems().addAll("Circle", "Rectangle");
    }

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

