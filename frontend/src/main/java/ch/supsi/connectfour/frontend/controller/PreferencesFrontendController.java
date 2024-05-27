package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
            this.preferencesView.initSaveListener(this.translationsController.translate("label.preferences_please_choose"), this.translationsController.translate("label.preferences_cannot_save") );

            preferencesView.setOnSaveButton((e) -> {
                // Handle saving preferences
                // todo: handle this in one method call (addALL)
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
        // TODO: not sure about this interaction. This frontend now depends both on its backend controller AND the translations contrller. Not sure if there's better ways to handle this
        //risolviamo sistemando i simboli
        this.preferencesView.setLanguages(this.translationsController.getSupportedLanguages());
        List<String> validSymbols = Stream.of(Symbol.values()).map(Enum::toString).toList();

        this.preferencesView.setShapes(validSymbols);
    }
    void initLabels() {
        // TODO: not sure if there's a cleaner way to do this :/
        // no, penso non ci sia un modo migliore dato che le stringe sono 'hardcodate'
        // al massimo si potrebbe pensare una lista di label e una lista di key e ciclarle insieme
        this.preferencesView.setBoxLanguageLabel(this.translationsController.translate("label.language_tag"));
        this.preferencesView.setPlayerOneColorLabel(this.translationsController.translate("label.player_one_color"));
        this.preferencesView.setPlayerOneShapeLabel(this.translationsController.translate("label.player_one_shape"));
        this.preferencesView.setPlayerTwoColorLabel(this.translationsController.translate("label.player_two_color"));
        this.preferencesView.setPlayerTwoShapeLabel(this.translationsController.translate("label.player_two_shape"));
        this.preferencesView.setSaveButtonLabel(this.translationsController.translate("label.save"));
        this.preferencesView.setCancelButtonLabel(this.translationsController.translate("label.cancel"));
        // TODO: maybe there's a cleaner way to do this, because this assumes that the default values do not allow the user to save, but if we were to change that we would have to change this too which is not optimal
        this.preferencesView.setPreferencesText(this.translationsController.translate("label.preferences_cannot_save"));
    }

    public void managePreferences(){
        stage.setResizable(false);
        stage.show();
    }
}
