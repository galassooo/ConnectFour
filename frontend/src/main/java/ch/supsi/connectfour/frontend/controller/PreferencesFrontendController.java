package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;

public class PreferencesFrontendController {

    private PreferencesView preferencesView;
    private static PreferencesFrontendController instance;
    private final PreferencesController backendController = PreferencesController.getInstance();
    private final TranslationsController translationsController = TranslationsController.getInstance();
    private final Stage stage = new Stage();

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

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();

            this.initViewChoices();
            this.initLabels();

            preferencesView.setOnSaveButton((e) -> {
                // Handle saving preferences
                String language = preferencesView.getSelectedLanguage();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("language-tag", language));

                String playerOneColor = preferencesView.getPlayerOneColor();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-one-color", playerOneColor));

                String playerTwoColor = preferencesView.getPlayerTwoColor();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-two-color", playerTwoColor));

                String playerOneShape = preferencesView.getPlayerOneShape();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-one-shape", playerOneShape));

                String playerTwoShape = preferencesView.getPlayerTwoShape();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-two-shape", playerTwoShape));

                stage.close();
            });

            preferencesView.setOnCancelButton((e) -> stage.close());

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/preferences/wrench.png"))));
            stage.setScene(scene);
            stage.setTitle(this.translationsController.translate("label.preferences"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void initViewChoices() {
        // TODO: not sure about this interaction. This frontend now depends both on its backend controller and the translations contrller. Not sure if there's better ways to handle this
        this.preferencesView.setLanguages(this.translationsController.getSupportedLanguages());
        // TODO: replace with the actual supported shapes, probably will require a minor refactor
        this.preferencesView.setShapes(List.of("C", "O", "R", "T", "I", "!!"));
    }
    void initLabels() {
        // TODO: not sure if there's a cleaner way to do this :/
        this.preferencesView.setBoxLanguageLabel(this.translationsController.translate("label.language_tag"));
        this.preferencesView.setPlayerOneColorLabel(this.translationsController.translate("label.player_one_color"));
        this.preferencesView.setPlayerOneShapeLabel(this.translationsController.translate("label.player_one_shape"));
        this.preferencesView.setPlayerTwoColorLabel(this.translationsController.translate("label.player_two_color"));
        this.preferencesView.setPlayerTwoShapeLabel(this.translationsController.translate("label.player_two_shape"));
        this.preferencesView.setSaveButtonLabel(this.translationsController.translate("label.save"));
        this.preferencesView.setCancelButtonLabel(this.translationsController.translate("label.cancel"));
    }

    public void managePreferences(){
        stage.setResizable(false);
        stage.show();
    }
}
