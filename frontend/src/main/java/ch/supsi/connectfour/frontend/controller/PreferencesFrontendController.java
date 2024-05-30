package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.PreferencesView;
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

public class PreferencesFrontendController {

    private PreferencesView preferencesView;
    private static PreferencesFrontendController instance;
    private final PreferencesModel model;
    private final TranslationModel translationModel;
    private final Stage stage;

    private final static String SYMBOL_REGEX = "/images/symbols/.*\\.PNG";

    public static PreferencesFrontendController getInstance() {
        if (instance == null) {
            instance = new PreferencesFrontendController();
        }
        return instance;
    }

    private PreferencesFrontendController() {
        translationModel = TranslationModel.getInstance();
        model = new PreferencesModel();
        stage = new Stage();
        try {
            URL fxmlUrl = getClass().getResource("/preferences.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl, translationModel.getUiBundle());
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();

            this.initViewChoices();
            this.preferencesView.initSaveListener(this.translationModel.translate("label.preferences_please_choose"), this.translationModel.translate("label.preferences_cannot_save"));
            this.preferencesView.setColorPickerLocale(model.getLanguage());

            // todo: è giusto che la view venga inizializzata qui dentro nel controller??
            preferencesView.setOnSaveButton((e) -> {
                List<AbstractMap.SimpleEntry<String, String>> preferences;
                preferences = List.of(
                        new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage()),
                        new AbstractMap.SimpleEntry<>("player-one-color", preferencesView.getPlayerOneColor()),
                        new AbstractMap.SimpleEntry<>("player-two-color", preferencesView.getPlayerTwoColor()),
                        new AbstractMap.SimpleEntry<>("player-one-symbol", String.valueOf(preferencesView.getPlayerOneShape().getValue())),
                        new AbstractMap.SimpleEntry<>("player-two-symbol", String.valueOf(preferencesView.getPlayerTwoShape().getValue()))
                );
                preferences.forEach((preference) -> model.setPreference(preference));

                stage.close();
            });

            preferencesView.setOnCancelButton((e) -> stage.close());

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preferences/gear.png"))));
            stage.setScene(scene);
            stage.setTitle(this.translationModel.translate("label.preferences"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initViewChoices() {
        this.preferencesView.setLanguages(this.translationModel.getSupportedLanguages());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Symbol> validSymbols = null;
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
                                    return new Symbol(value, name);
                                }
                                return null;
                            }).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.preferencesView.setShapes(validSymbols);
    }

    public void managePreferences() {
        stage.setResizable(false);
        stage.show();
    }
}
