package ch.supsi.connectfour.frontend.controller.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.LanguageOnlyRequired;
import ch.supsi.connectfour.frontend.model.preferences.PreferencesModel;
import ch.supsi.connectfour.frontend.model.translations.TranslationModel;
import ch.supsi.connectfour.frontend.view.preferences.IPreferencesView;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PreferencesFrontendController implements IPreferencesController, EventHandler<LanguageOnlyRequired> {

    public static final String IMAGES_SYMBOLS = "images/symbols/";
    /* regex */
    private final static String SYMBOL_REGEX = "/images/symbols/.*\\.PNG";
    /* self reference */
    private static PreferencesFrontendController instance;
    /* models */
    private final PreferencesModel model;
    private final TranslationModel translationModel;

    /* stage */
    private final Stage stage;

    /* view */
    private IPreferencesView preferencesView;

    /**
     * Constructor handling the initialization of the components needed
     */
    private PreferencesFrontendController() {

        translationModel = TranslationModel.getInstance();
        String pleaseChoose = translationModel.translate("label.preferences_please_choose");
        String cannotSave = translationModel.translate("label.preferences_cannot_save");
        String languageOnly = translationModel.translate("label.preferences_language_only");
        // Instantiate the model and provide the translations it needs
        model = new PreferencesModel(pleaseChoose, cannotSave, languageOnly);
        stage = new Stage();
        stage.addEventHandler(LanguageOnlyRequired.LANGUAGE_CHANGE, this);

        try {
            URL fxmlUrl = getClass().getResource("/preferences.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl, translationModel.getUiBundle());
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();
            // Provides the model to the view
            preferencesView.setModel(model);
            preferencesView.setStage(stage);
            // This is not ideal but unfortunately the color picker uses the default locale to
            // determine the labels for the languages it displays, so to fully translate every
            // aspect of the application, this approach was needed.
            preferencesView.setColorPickerLocale(translationModel.getCurrentLanguage());
            preferencesView.setOnCancelButton((e) -> stage.close());

            // How the view should behave if the save button is pressed
            preferencesView.setOnSaveButton((e) -> {
                // This was done to allow the user to save language preferences independently of the color - symbol choices.
                // If the color - symbol combination is not valid, the user is still allowed to save the preferences, but it will only save the new language.
                if (model.isOnlyLanguageRequested()) {
                    var value = new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage());
                    model.setPreference(value);
                    stage.close();
                    return;
                }
                // Retrieves the preferences selected by the user
                List<AbstractMap.SimpleEntry<String, String>> preferences = getPreferences();
                preferences.forEach(model::setPreference);
                stage.close();
            });
            // Provide the view with the information on supported languages
            preferencesView.setLanguages(this.translationModel.getSupportedLanguages());
            this.initViewChoices();

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preferences/gear.png"))));
            stage.setScene(scene);
            stage.setTitle(translationModel.translate("label.preferences"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return an instance of this class
     */
    public static PreferencesFrontendController getInstance() {
        if (instance == null) {
            instance = new PreferencesFrontendController();
        }
        return instance;
    }

    /**
     * @return a list of key-value pairs representing the preferences selected by the user in the preferences menu
     */
    private @NotNull List<AbstractMap.SimpleEntry<String, String>> getPreferences() {
        List<AbstractMap.SimpleEntry<String, String>> preferences;
        preferences = List.of(
                new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage()),
                new AbstractMap.SimpleEntry<>("player-one-color", preferencesView.getPlayerOneColor()),
                new AbstractMap.SimpleEntry<>("player-two-color", preferencesView.getPlayerTwoColor()),
                new AbstractMap.SimpleEntry<>("player-one-symbol", String.valueOf(preferencesView.getPlayerOneShape().getValue())),
                new AbstractMap.SimpleEntry<>("player-two-symbol", String.valueOf(preferencesView.getPlayerTwoShape().getValue()))
        );
        return preferences;
    }

    /**
     * Initializes the choices in the symbol combo boxes in the preferences views. This method first loads all available symbols
     * from the resources, then provides the list of retrieved symbols to the view for initialization
     */
    private void initViewChoices() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<SymbolBusiness> validSymbols = null;
        try {
            validSymbols =
                    Stream.of(resolver.getResources(String.format("classpath:%s*.PNG", IMAGES_SYMBOLS))) // First load all resources from where the symbols are located
                            .map((r) -> {
                                String absolutePath = "";
                                try {
                                    absolutePath = r.getURL().toString();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // For consistency across OSs, convert Windows-style separators into Unix-style file separators
                                absolutePath = absolutePath.replace("\\", "/");

                                // We are using regexes to match the part of the absolute path we are interested in.
                                // This is done because we want to have a relative path from the images directory "onwards"
                                // instead of the full absolute path, for portability reasons.
                                Pattern pattern = Pattern.compile(SYMBOL_REGEX);
                                Matcher matcher = pattern.matcher(absolutePath);

                                if (matcher.find()) {
                                    // Get the relative path
                                    String relativePath = matcher.group();
                                    // At this point, value should be in the form ... / images / symbol / ...png
                                    // so this splits around the occurrences of / and only takes the last element
                                    String fileName = relativePath.split("/")[3];
                                    // Removes the extension. This approach is a bit flawed as it relies on the images being pngs, so it could be improved
                                    fileName = fileName.substring(0, fileName.length() - 4).toUpperCase();
                                    // The relative path and the filename are wrapped in a Symbol object and returned
                                    return new SymbolBusiness(relativePath, fileName);
                                }
                                return null;
                            }).toList();
        } catch (IOException e) {
            System.err.printf("Could not load symbols from %s%n" + IMAGES_SYMBOLS);
            e.printStackTrace();
        }
        this.preferencesView.setSymbols(validSymbols);
    }

    /**
     * Shows the preferences popup
     */
    public void managePreferences() {
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void handle(@NotNull LanguageOnlyRequired languageOnlyRequired) {
        model.setLanguageOnlyRequested(languageOnlyRequired.isLanguageOnlyRequired());
    }
}