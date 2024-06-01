package ch.supsi.connectfour.frontend.view.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.IPreferencesModel;
import ch.supsi.connectfour.frontend.model.preferences.LanguageOnlyRequired;
import ch.supsi.connectfour.frontend.model.translations.ITranslationsModel;
import ch.supsi.connectfour.frontend.model.translations.TranslationModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public class PreferencesView implements IPreferencesView {

    private final ITranslationsModel translationModel;
    /* symbol list - field */
    private final List<ComboBox<SymbolBusiness>> playerShapeBoxes = new ArrayList<>();
    private final List<ColorPicker> colorPickers = new ArrayList<>();
    private final List<ComboBoxBase<?>> comboBoxes = new ArrayList<>();
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
    private Stage root;


    public PreferencesView() {
        translationModel = TranslationModel.getInstance();
    }

    @FXML
    void initialize() {
        /*
         * The following lines simply aggregate together similar objects in a List, in order to simplify
         * interactions with all elements at once through stream operations
         */
        playerShapeBoxes.add(playerOneShapeComboBox);
        playerShapeBoxes.add(playerTwoShapeComboBox);

        colorPickers.add(playerOneColorPicker);
        colorPickers.add(playerTwoColorPicker);

        comboBoxes.addAll(playerShapeBoxes);
        comboBoxes.addAll(colorPickers);
        comboBoxes.add(languageComboBox);

        // Colors equal binding. Returns true if both colors are null or if the two colors are equal. Used to disable the save
        // button dynamically
        BooleanBinding colorsEqualBinding = Bindings.createBooleanBinding(() -> {
            Object playerOneColor = playerOneColorPicker.getValue();
            Object playerTwoColor = playerTwoColorPicker.getValue();

            return (playerOneColor == null && playerTwoColor == null) ||
                    (playerOneColor != null && playerOneColor.equals(playerTwoColor));
        }, playerOneColorPicker.valueProperty(), playerTwoColorPicker.valueProperty());

        //shapes equal binding. Returns true if both shapes are null or if the two shapes are equal. Used to disable the save
        // button dynamically
        BooleanBinding shapesEqualBinding = Bindings.createBooleanBinding(() -> {
            Object playerOneShape = playerOneShapeComboBox.getValue();
            Object playerTwoShape = playerTwoShapeComboBox.getValue();

            return (playerOneShape == null && playerTwoShape == null) ||
                    (playerOneShape != null && playerOneShape.equals(playerTwoShape));
        }, playerOneShapeComboBox.valueProperty(), playerTwoShapeComboBox.valueProperty());

        //language equal binding. Returns true if the user changed the language in the preferences
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

        // Bind the the binding to the disable property of the button, so that if the binding returns true the button
        // is disabled, else enabled
        saveButton.disableProperty().bind(saveButtonDisabledBinding);

        // Function to update preferencesText based on current state
        Runnable updatePreferencesText = () -> {
            boolean colorsEqual = colorsEqualBinding.get();
            boolean shapesEqual = shapesEqualBinding.get();
            boolean languageEqual = languageEqualBinding.get();

            /* Gli eventi sono stati creati per far si che il controller possa gestire l'evento per cui solo la lingua è valida.
             * In caso contrario avremmo dovuto settare da qui il campo model.languageOnlyRequired ma la view non dovrebbe
             * modificare direttamente i campi del model. dunque:
             *
             * viene generato un evento -> il controller lo cattura -> setta il campo del model in base all'evento generato
             */
            if (!languageEqual && colorsEqual && shapesEqual) {
                this.preferencesText.setText(model.getLanguageOnlyMessage());
                Event.fireEvent(root, new LanguageOnlyRequired(true));

            } else if (colorsEqual && shapesEqual) {
                this.preferencesText.setText(model.getDisableMessage());
                Event.fireEvent(root, new LanguageOnlyRequired(false));

            } else {
                this.preferencesText.setText(model.getEnableMessage());
                Event.fireEvent(root, new LanguageOnlyRequired(false));
            }
        };

        // Add listeners to color and shape pickers to update preferences text
        comboBoxes.forEach((cBox) -> cBox.valueProperty().addListener((observable, oldValue, newValue) -> updatePreferencesText.run()));

        preferencesText.setText(" \n ");
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

    /* setters */
    @Override
    public void setModel(@NotNull IPreferencesModel model) {
        this.model = model;
    }

    @Override
    public void setStage(Stage stage) {
        root = stage;
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
}

