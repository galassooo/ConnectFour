package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.model.PreferencesModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
            // TODO: fix implemenation of getUiBundle (see comment in there)
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

        List<String> validSymbols = new ArrayList<>();
        URL resource = getClass().getResource("/images/symbols");
        if (resource != null) {
            String protocol = resource.getProtocol();
            if ("jar".equals(protocol)) {
                processJarDirectory(resource, validSymbols);
            } else if ("file".equals(protocol)) {
                processFileDirectory(resource, validSymbols);
            }
        }
        this.preferencesView.setShapes(validSymbols);
    }

    private void processJarDirectory(URL resource, List<String> validSymbols) {
        String decodedPath = URLDecoder.decode(resource.getPath(), StandardCharsets.UTF_8);
        String jarPath = decodedPath.substring(5, decodedPath.indexOf("!"));
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith("images/symbols") && entryName.endsWith(".png")) {
                    String fileName = Paths.get(entryName).getFileName().toString();
                    validSymbols.add(fileName.substring(0, fileName.length() - 4)); // remove the .png suffix
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFileDirectory(URL resource, List<String> validSymbols) {
        try (Stream<Path> paths = Files.walk(Paths.get(resource.toURI()))) {
            paths.filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(fileName -> fileName.toLowerCase().endsWith(".png"))
                    .map(fileName -> fileName.substring(0, fileName.length() - 4)) // remove the .png suffix
                    .forEach(validSymbols::add);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void managePreferences() {
        stage.setResizable(false);
        stage.show();
    }
}
