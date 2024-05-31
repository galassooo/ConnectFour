package ch.supsi.connectfour.frontend.view.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
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
    private PreferencesModel model;
    private final TranslationModel translationModel;

    /* symbol list - field */
    List<ComboBox<SymbolBusiness>> playerShapeBoxes = new ArrayList<>();

    public PreferencesView() {
        translationModel = TranslationModel.getInstance();
    }

    //ALEX (magari fai un commento dettagliato dentro)
    @FXML
    void initialize() {
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);

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

            if(newLanguage!= null){
                newLanguage = newLanguage.replace('-', '_');
            }

            return Objects.equals(oldLanguage, newLanguage);
        }, languageComboBox.valueProperty());

        //construct a single binding obj given the others
        BooleanBinding saveButtonDisabledBinding = colorsEqualBinding.and(shapesEqualBinding).and(languageEqualBinding);

        //bind the button to the booleanBinding
        saveButton.disableProperty().bind(saveButtonDisabledBinding);

        saveButton.disableProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.preferencesText.setText(model.getDisableMessage());
            } else {

                // Check if only the language has changed
                boolean colorsEqual = colorsEqualBinding.get();
                boolean shapesEqual = shapesEqualBinding.get();
                boolean languageEqual = languageEqualBinding.get();

                //ALEX dimmi se per te va bene che la view aggiorni lo stato del model in base all'input... non saprei io..
                if (!languageEqual && colorsEqual && shapesEqual) {
                    this.preferencesText.setText(model.getLanguageOnlyMessage());
                    model.setLanguageOnlyRequested(true);
                } else {
                    this.preferencesText.setText(model.getEnableMessage());
                    model.setLanguageOnlyRequested(false);
                }
            }
        });

        preferencesText.setText(" \n ");
    }

    /* setters */
    @Override
    public void setModel(@NotNull PreferencesModel model) {
        this.model = model;
    }

    @Override
    public void setShapes(List<SymbolBusiness> supportedShapes) {
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

