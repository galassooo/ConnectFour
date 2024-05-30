package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class PreferencesView {
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

    @FXML
    void initialize() {
        // TODO: not sure if this is worth doing, especially considering that in this context, we will only ever have two players
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);

        // TODO: not a fan of this because we are defining logic inside a view, but not sure how to handle this since otherwise I'd need to get access to UI elements from the outside (having references to the needed UI elements in the frontend controller) which is ehhhh
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
                }, playerOneColorPicker.valueProperty(),
                playerTwoColorPicker.valueProperty(),
                playerOneShapeComboBox.valueProperty(),
                playerTwoShapeComboBox.valueProperty());

        preferencesText.setText(" \n ");
        saveButton.disableProperty().bind(saveButtonDisabledBinding);
    }

    public void initSaveListener(String enableMessage, String disableMessage) {
        saveButton.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                this.preferencesText.setText(enableMessage);
            } else {
                this.preferencesText.setText(disableMessage);
            }
        });
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

    public PreferencesView() {
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
        System.out.println(playerOneShapeComboBox.getValue());
        return playerOneShapeComboBox.getValue();
    }

    public Symbol getPlayerTwoShape() {
        return playerTwoShapeComboBox.getValue();
    }
}

