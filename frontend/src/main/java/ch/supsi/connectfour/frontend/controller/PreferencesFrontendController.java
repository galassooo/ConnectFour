package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PreferencesFrontendController {

    private PreferencesView preferencesView;
    private static PreferencesFrontendController instance;
    private PreferencesModel model;
    private final TranslationsController translationsController = TranslationsController.getInstance();

    private final TranslationModel translationModel = TranslationModel.getInstance();
    private final Stage stage = new Stage();
    private List<String> validSymbols;

    public static PreferencesFrontendController getInstance() {
        if (instance == null) {
            instance = new PreferencesFrontendController();
        }
        return instance;
    }

    private PreferencesFrontendController() {
        try {
            URL fxmlUrl = getClass().getResource("/preferences.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl, translationModel.getUiBundle());
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();
            model = new PreferencesModel();

            this.initViewChoices();
            this.preferencesView.initSaveListener(this.translationsController.translate("label.preferences_please_choose"), this.translationsController.translate("label.preferences_cannot_save"));
            this.preferencesView.setColorPickerLocale(model.getLanguage());

            // todo: è giusto che la view venga inizializzata qui dentro nel controller??
            preferencesView.setOnSaveButton((e) -> {
                List<AbstractMap.SimpleEntry<String, String>> preferences;
                preferences = List.of(
                        new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage()),
                        new AbstractMap.SimpleEntry<>("player-one-color", preferencesView.getPlayerOneColor()),
                        new AbstractMap.SimpleEntry<>("player-two-color", preferencesView.getPlayerTwoColor()),
                        // TODO: CHANGE SHAPE -> SYMBOL FOR CONSISTENCY
                        new AbstractMap.SimpleEntry<>("player-one-symbol", preferencesView.getPlayerOneShape()),
                        new AbstractMap.SimpleEntry<>("player-two-symbol", preferencesView.getPlayerTwoShape())
                );
                preferences.forEach((preference) -> model.setPreference(preference));

                stage.close();
            });

            preferencesView.setOnCancelButton((e) -> stage.close());

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preferences/gear.png"))));
            stage.setScene(scene);
            stage.setTitle(this.translationsController.translate("label.preferences"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initViewChoices() {
        this.preferencesView.setLanguages(this.translationsController.getSupportedLanguages());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            validSymbols = Arrays.stream(resolver.getResources(String.format("classpath:%s*.PNG", "images/symbols/")))
                    .map((r) -> {
                        Path fullPath = null;
                        try {
                            fullPath = Paths.get(r.getURI());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Path basePath = Paths.get("/images/symbols/");

                        // Find the index of the base directory in the full path
                        int baseIndex = fullPath.toString().indexOf(basePath.toString());

                        if (baseIndex != -1) {
                            // Extract the relative path
                            String relativePath = fullPath.toString().substring(baseIndex);
                            // Convert to a string with forward slashes
                            return relativePath.replace("\\", "/");
                        } else {
                            ;
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
