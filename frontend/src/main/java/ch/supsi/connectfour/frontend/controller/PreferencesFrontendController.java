package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class PreferencesFrontendController {

    private PreferencesView preferencesView;
    private static PreferencesFrontendController instance;
    private final PreferencesController backendController = PreferencesController.getInstance();
    private final TranslationsController translationsController = TranslationsController.getInstance();

    private final TranslationModel translationModel = TranslationModel.getInstance();
    private final Stage stage = new Stage();

    private final static String BUNDLE_NAME = "i18n/UI.ui_labels";

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
            // TODO: not sure if this should be loaded here (resource bundle)
            FXMLLoader loader = new FXMLLoader(fxmlUrl, translationModel.getUiBundle());
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();

            this.initViewChoices();
            this.preferencesView.initSaveListener(this.translationsController.translate("label.preferences_please_choose"), this.translationsController.translate("label.preferences_cannot_save"));

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
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-one-shape",String.format(shapePreferencePattern,playerOneShape)));

                String playerTwoShape = preferencesView.getPlayerTwoShape();
                backendController.setPreference(new AbstractMap.SimpleEntry<>("player-two-shape", String.format(shapePreferencePattern,playerTwoShape)));

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

        //Guardare nella directory images/symbols/.... e fare uno split e prendere solo la parte prima dell PNG
        List<String> validSymbols = new ArrayList<>();
        URL resource = getClass().getResource("/images/symbols");
        if (resource != null) {
            try (Stream<Path> paths = Files.walk(Path.of(resource.toURI()))) {
                paths.filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .filter(fileName -> fileName.toLowerCase().endsWith(".png"))
                        .map(fileName -> fileName.substring(0, fileName.length() - 4)) // remove the .png suffix
                        .forEach(validSymbols::add);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        this.preferencesView.setShapes(validSymbols);
    }

    public void managePreferences() {
        stage.setResizable(false);
        stage.show();
    }
}
