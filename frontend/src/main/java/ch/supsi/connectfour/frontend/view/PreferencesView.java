package ch.supsi.connectfour.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PreferencesView {
    @FXML
    public Label boxLanguageLabel;
    @FXML
    public Label playerOneColorLabel;
    @FXML
    public Label playerOneShapeLabel;
    @FXML
    public Label playerTwoColorLabel;
    @FXML
    public Label playerTwoShapeLabel;
    @FXML
    private BorderPane borderPane;
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
    public void setSaveButtonLabel(String text) {
        this.saveButton.setText(text);
    }
    public void setCancelButtonLabel(String text) {
        this.cancelButton.setText(text);
    }

    public void setBoxLanguageLabel(String text) {
        this.boxLanguageLabel.setText(text);
    }

    public void setPlayerOneColorLabel(String text) {
        this.playerOneColorLabel.setText(text);
    }

    public void setPlayerOneShapeLabel(String text) {
        this.playerOneShapeLabel.setText(text);
    }

    public void setPlayerTwoColorLabel(String text) {
        this.playerTwoColorLabel.setText(text);
    }

    public void setPlayerTwoShapeLabel(String text) {
        this.playerTwoShapeLabel.setText(text);
    }

    public void setShapes(List<String> supportedShapes) {
        playerShapeBoxes.forEach((cBox) -> {
            // Adds all supported shapes to the combo boxes and automatically selects the first option, to avoid having a blank cell
            cBox.getItems().addAll(supportedShapes);
            cBox.getSelectionModel().selectFirst();
            // Prevents displaying a list of more than 3 elements, nice addition if we end up having a lot of options and don't want to have a very long drop down menu
            cBox.setVisibleRowCount(3);
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

