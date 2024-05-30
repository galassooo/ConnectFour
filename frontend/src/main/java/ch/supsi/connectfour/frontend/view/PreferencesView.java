package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class PreferencesView implements IPreferencesView {
    @FXML
    public Text preferencesText;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private ColorPicker playerOneColorPicker;

    @FXML
    private ColorPicker playerTwoColorPicker;

    @FXML
    private ComboBox<Symbol> playerOneShapeComboBox;

    @FXML
    private ComboBox<Symbol> playerTwoShapeComboBox;
    List<ComboBox<Symbol>> playerShapeBoxes = new ArrayList<>();

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;
    private PreferencesModel model;

    @FXML
    void initialize() {
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);

        // Binds this condition to the save button, enabling or disabling it depending on if the condition is met
        BooleanBinding saveButtonDisabledBinding = Bindings.createBooleanBinding(() -> {
            Object playerOneColor = playerOneColorPicker.getValue();
            Object playerTwoColor = playerTwoColorPicker.getValue();
            Object playerOneShape = playerOneShapeComboBox.getValue();
            Object playerTwoShape = playerTwoShapeComboBox.getValue();

            // Check for null values before comparing
            boolean colorsEqual = (playerOneColor == null && playerTwoColor == null) ||
                    (playerOneColor != null && playerOneColor.equals(playerTwoColor));
            boolean shapesEqual = (playerOneShape == null && playerTwoShape == null) ||
                    (playerOneShape != null && playerOneShape.equals(playerTwoShape));

            return colorsEqual && shapesEqual;
        }, playerOneColorPicker.valueProperty(), playerTwoColorPicker.valueProperty(), playerOneShapeComboBox.valueProperty(), playerTwoShapeComboBox.valueProperty());
        saveButton.disableProperty().bind(saveButtonDisabledBinding);

        saveButton.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                this.preferencesText.setText(model.getEnableMessage());
            } else {
                this.preferencesText.setText(model.getDisableMessage());
            }
        });
        preferencesText.setText(" \n ");
    }

    public PreferencesView(){}

    public void setModel(@NotNull PreferencesModel model) {
        this.model = model;
    }

    public void setShapes(List<Symbol> supportedShapes) {
        playerShapeBoxes.forEach((cBox) -> {
            // Adds all supported shapes to the combo boxes and automatically selects the first option, to avoid having a blank cell
            cBox.getItems().addAll(supportedShapes);
            cBox.getSelectionModel().selectFirst();
            // Prevents displaying a list of more than 3 elements, nice addition if we end up having a lot of options and don't want to have a very long drop down menu
            cBox.setVisibleRowCount(3);
        });
    }

    public void setColorPickerLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    public void setLanguages(List<String> supportedLanguages) {
        languageComboBox.getItems().setAll(supportedLanguages);
        languageComboBox.getSelectionModel().selectFirst();
    }


    // TODO: ?????
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

    public Symbol getPlayerOneShape() {
        return playerOneShapeComboBox.getValue();
    }

    public Symbol getPlayerTwoShape() {
        return playerTwoShapeComboBox.getValue();
    }
}

