package ch.supsi.connectfour.frontend.controller.preferences;

import ch.supsi.connectfour.backend.business.symbols.SymbolBusiness;
import ch.supsi.connectfour.frontend.model.preferences.PreferencesModel;
import ch.supsi.connectfour.frontend.model.translations.TranslationModel;
import ch.supsi.connectfour.frontend.view.preferences.IPreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PreferencesFrontendController implements IPreferencesController {

    /* self reference */
    private static PreferencesFrontendController instance;

    /* regex */
    private final static String SYMBOL_REGEX = "/images/symbols/.*\\.PNG";

    /* models */
    private final PreferencesModel model;
    private final TranslationModel translationModel;

    /* stage */
    private final Stage stage;

    /* view */
    private IPreferencesView preferencesView;

    //ALEX
    private PreferencesFrontendController() {
        translationModel = TranslationModel.getInstance();
        String pleaseChoose = translationModel.translate("label.preferences_please_choose");
        String cannotSave = translationModel.translate("label.preferences_cannot_save");
        String languageOnly = translationModel.translate("label.preferences_language_only");
        model = new PreferencesModel(pleaseChoose, cannotSave, languageOnly);
        stage = new Stage();
        try {
            URL fxmlUrl = getClass().getResource("/preferences.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl, translationModel.getUiBundle());
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();
            preferencesView.setModel(model);
            preferencesView.setColorPickerLocale(translationModel.getCurrentLanguage());
            preferencesView.setOnCancelButton((e) -> stage.close());

            preferencesView.setOnSaveButton((e) -> {

                if(model.isLanguageOnlyRequested()){
                    var value =  new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage());
                    model.setPreference(value);
                    stage.close();
                    return;
                }

                List<AbstractMap.SimpleEntry<String, String>> preferences;
                preferences = List.of(
                        new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage()),
                        new AbstractMap.SimpleEntry<>("player-one-color", preferencesView.getPlayerOneColor()),
                        new AbstractMap.SimpleEntry<>("player-two-color", preferencesView.getPlayerTwoColor()),
                        new AbstractMap.SimpleEntry<>("player-one-symbol", String.valueOf(preferencesView.getPlayerOneShape().getValue())),
                        new AbstractMap.SimpleEntry<>("player-two-symbol", String.valueOf(preferencesView.getPlayerTwoShape().getValue()))
                );
                preferences.forEach(model::setPreference);
                stage.close();
            });
            this.initViewChoices();

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preferences/gear.png"))));
            stage.setScene(scene);
            stage.setTitle(translationModel.translate("label.preferences"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return an instance of this class
     */
    public static PreferencesFrontendController getInstance() {
        if (instance == null) {
            instance = new PreferencesFrontendController();
        }
        return instance;
    }

    //ALEX
    private void initViewChoices() {
        this.preferencesView.setLanguages(this.translationModel.getSupportedLanguages());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<SymbolBusiness> validSymbols = null;
        try {
            validSymbols =
                    Stream.of(resolver.getResources(String.format("classpath:%s*.PNG", "images/symbols/")))
                            .map((r) -> {
                                String absolutePath = "";
                                try {
                                    absolutePath = r.getURL().toString();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                // For consistency across OSs, convert Windows-style separators into Unix-style file separators
                                absolutePath = absolutePath.replace("\\", "/");

                                Pattern pattern = Pattern.compile(SYMBOL_REGEX);
                                Matcher matcher = pattern.matcher(absolutePath);

                                if (matcher.find()) {
                                    // Il percorso relativo
                                    String value = matcher.group();
                                    // Separo in [] / [images] / [symbol] / [...png]
                                    String name = value.split("/")[3];
                                    // Tolgo il .png
                                    name = name.substring(0, name.length() - 4).toUpperCase();
                                    return new SymbolBusiness(value, name);
                                }
                                return null;
                            }).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.preferencesView.setShapes(validSymbols);
    }

    /**
     * shows the preferences popup
     */
    public void managePreferences() {
        stage.setResizable(false);
        stage.show();
    }
}
