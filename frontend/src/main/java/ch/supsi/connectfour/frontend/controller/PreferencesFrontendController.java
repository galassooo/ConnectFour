package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
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
import java.util.*;
import java.util.stream.Stream;

public class PreferencesFrontendController {

    private PreferencesView preferencesView;
    private static PreferencesFrontendController instance;
    private PreferencesModel model;
    private final TranslationsController translationsController = TranslationsController.getInstance();

    private final TranslationModel translationModel = TranslationModel.getInstance();
    private final Stage stage = new Stage();

    private final static String shapePreferencePattern = "/images/symbols/%s.PNG";

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

            // todo: è giusto che la view venga inizializzata qui dentro nel controller??
            preferencesView.setOnSaveButton((e) -> {
                List<AbstractMap.SimpleEntry<String, String>> preferences;
                preferences = List.of(
                        new AbstractMap.SimpleEntry<>("language-tag", preferencesView.getSelectedLanguage()),
                        new AbstractMap.SimpleEntry<>("player-one-color", preferencesView.getPlayerOneColor()),
                        new AbstractMap.SimpleEntry<>("player-two-color", preferencesView.getPlayerTwoColor()),
                        // TODO: CHANGE SHAPE -> SYMBOL FOR CONSISTENCY
                        new AbstractMap.SimpleEntry<>("player-one-shape", preferencesView.getPlayerOneShape()),
                        new AbstractMap.SimpleEntry<>("player-two-shape", preferencesView.getPlayerTwoShape())
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
        List<String> validSymbols = null;
        try {
            validSymbols =
                    Stream.of(resolver.getResources(String.format("classpath:%s*.PNG", "images/symbols/")))
                            .map((resource -> {
                                String rAsString = resource.toString();
                                // Remove the .png
                                return rAsString.substring(0, rAsString.length() - 4);
                            })).toList(); // TODO: add constant for URL
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
