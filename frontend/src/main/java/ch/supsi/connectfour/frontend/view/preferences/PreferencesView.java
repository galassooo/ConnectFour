package ch.supsi.connectfour.frontend.view.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.IPreferencesModel;
import ch.supsi.connectfour.frontend.model.preferences.PreferencesModel;
import ch.supsi.connectfour.frontend.model.translations.ITranslationsModel;
import ch.supsi.connectfour.frontend.model.translations.TranslationModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public class PreferencesView implements IPreferencesView {

    /* components */
    @FXML
    public Text preferencesText;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private ColorPicker playerOneColorPicker;
    @FXML
    private ColorPicker playerTwoColorPicker;
    @FXML
    private ComboBox<SymbolBusiness> playerOneShapeComboBox;
    @FXML
    private ComboBox<SymbolBusiness> playerTwoShapeComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    /* model */
    private IPreferencesModel model;
    private final ITranslationsModel translationModel;

    /* symbol list - field */
    List<ComboBox<SymbolBusiness>> playerShapeBoxes = new ArrayList<>();
    List<ColorPicker> colorPickers = new ArrayList<>();
    List<ComboBoxBase<?>> comboBoxes = new ArrayList<>();

    public PreferencesView() {
        translationModel = TranslationModel.getInstance();
    }

    //ALEX (magari fai un commento dettagliato dentro)
    @FXML
    void initialize() {
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);

        colorPickers.add(playerOneColorPicker);
        colorPickers.add(playerTwoColorPicker);

        comboBoxes.addAll(playerShapeBoxes);
        comboBoxes.addAll(colorPickers);
        comboBoxes.add(languageComboBox);

        //colors equal binding
        BooleanBinding colorsEqualBinding = Bindings.createBooleanBinding(() -> {
            Object playerOneColor = playerOneColorPicker.getValue();
            Object playerTwoColor = playerTwoColorPicker.getValue();

            return (playerOneColor == null && playerTwoColor == null) ||
                    (playerOneColor != null && playerOneColor.equals(playerTwoColor));
        }, playerOneColorPicker.valueProperty(), playerTwoColorPicker.valueProperty());

        //shapes equal binding
        BooleanBinding shapesEqualBinding = Bindings.createBooleanBinding(() -> {
            Object playerOneShape = playerOneShapeComboBox.getValue();
            Object playerTwoShape = playerTwoShapeComboBox.getValue();

            return (playerOneShape == null && playerTwoShape == null) ||
                    (playerOneShape != null && playerOneShape.equals(playerTwoShape));
        }, playerOneShapeComboBox.valueProperty(), playerTwoShapeComboBox.valueProperty());

        //language equal binding
        BooleanBinding languageEqualBinding = Bindings.createBooleanBinding(() -> {
            String oldLanguage = translationModel.getCurrentLanguage().toString();
            String newLanguage = languageComboBox.getValue();

            if (newLanguage != null) {
                newLanguage = newLanguage.replace('-', '_');
            }

            return Objects.equals(oldLanguage, newLanguage);
        }, languageComboBox.valueProperty());

        //construct a single binding obj given the others
        BooleanBinding saveButtonDisabledBinding = colorsEqualBinding.and(shapesEqualBinding).and(languageEqualBinding);

        //bind the button to the booleanBinding
        saveButton.disableProperty().bind(saveButtonDisabledBinding);

        // Function to update preferencesText based on current state

        /*
         * TODO: This interaction is flawed. The view should not directly modify the model. Instead, the controller should coordinate operations between model and view.
         */
        Runnable updatePreferencesText = () -> {
            boolean colorsEqual = colorsEqualBinding.get();
            boolean shapesEqual = shapesEqualBinding.get();
            boolean languageEqual = languageEqualBinding.get();

            if (!languageEqual && colorsEqual && shapesEqual) {
                this.preferencesText.setText(model.getLanguageOnlyMessage());
                model.setLanguageOnlyRequested(true);
            } else if (colorsEqual && shapesEqual) {
                this.preferencesText.setText(model.getDisableMessage());
                model.setLanguageOnlyRequested(false);
            } else {
                this.preferencesText.setText(model.getEnableMessage());
                model.setLanguageOnlyRequested(false);
            }
        };

        // Add listeners to color and shape pickers to update preferences text
        comboBoxes.forEach((cBox) -> cBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePreferencesText.run()));

        preferencesText.setText(" \n ");
    }

    /* setters */
    @Override
    public void setModel(@NotNull IPreferencesModel model) {
        this.model = model;
    }

    @Override
    public void setSymbols(List<SymbolBusiness> supportedShapes) {
        playerShapeBoxes.forEach((cBox) -> {
            // Adds all supported shapes to the combo boxes and automatically selects the first option, to avoid having a blank cell
            cBox.getItems().addAll(supportedShapes);
            cBox.getSelectionModel().selectFirst();
            // Prevents displaying a list of more than 3 elements, nice addition if we end up having a lot of options and don't want to have a very long drop down menu
            cBox.setVisibleRowCount(3);
        });
    }

    @Override
    public void setColorPickerLocale(Locale locale) {
        Locale.setDefault(locale);
    }

    @Override
    public void setLanguages(List<String> supportedLanguages) {
        languageComboBox.getItems().setAll(supportedLanguages);
        languageComboBox.getSelectionModel().selectFirst();
    }

    @Override
    public void setOnSaveButton(@NotNull Consumer<ActionEvent> eventConsumer) {
        saveButton.setOnAction(eventConsumer::accept);
    }

    @Override
    public void setOnCancelButton(@NotNull Consumer<ActionEvent> eventConsumer) {
        cancelButton.setOnAction(eventConsumer::accept);
    }

    /* getters */
    @Override
    public String getSelectedLanguage() {
        return languageComboBox.getValue();
    }

    @Override
    public String getPlayerOneColor() {
        return playerOneColorPicker.getValue().toString();
    }

    @Override
    public String getPlayerTwoColor() {
        return playerTwoColorPicker.getValue().toString();
    }

    @Override
    public SymbolBusiness getPlayerOneShape() {
        return playerOneShapeComboBox.getValue();
    }

    @Override

    public SymbolBusiness getPlayerTwoShape() {
        return playerTwoShapeComboBox.getValue();
    }
}

