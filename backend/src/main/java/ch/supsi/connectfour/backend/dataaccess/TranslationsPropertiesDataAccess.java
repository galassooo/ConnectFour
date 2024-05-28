package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {
    // TODO: figure out why this explodes with File.separator
    //TODO: implementare separator lol
    private static final String LABELS_PATH = "/i18n/labels";

    private static final String UI_PATH = "/i18n/UI";
    private static final String supportedLanguagesPath = "/supported-languages.properties";

    public static TranslationsPropertiesDataAccess myself;

    protected TranslationsPropertiesDataAccess() {
    }

    // singleton instantiation of this data access object
    // guarantees only a single instance exists in the life of the application
    public static TranslationsPropertiesDataAccess getInstance() {
        if (myself == null) {
            myself = new TranslationsPropertiesDataAccess();
        }

        return myself;
    }

    private @NotNull Properties loadSupportedLanguageTags() {
        Properties supportedLanguageTags = new Properties();
        try {
            InputStream supportedLanguageTagsStream = this.getClass().getResourceAsStream(supportedLanguagesPath);
            supportedLanguageTags.load(supportedLanguageTagsStream);

        } catch (IOException ignored) {
            ;
        }

        // return the properties object with the loaded preferences
        return supportedLanguageTags;
    }

    @Override
    public List<String> getSupportedLanguageTags() {
        ArrayList<String> supportedLanguageTags = new ArrayList<>();

        Properties props = this.loadSupportedLanguageTags();
        for (Object key : props.keySet()) {
            supportedLanguageTags.add(props.getProperty((String) key));
        }

        return supportedLanguageTags;
    }

    @Override
    public Properties getTranslations(Locale locale) {
        final Properties translations = new Properties();

        try {
            // Access resources using the class loader
            URL resource = this.getClass().getResource(LABELS_PATH);
            if (resource != null) {
                Path path = Paths.get(resource.toURI());
                Files.walk(path)
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".properties"))
                        .forEach(p -> loadTranslationsFromFile(p, locale, translations));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return translations;
    }

    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        String baseName = "i18n" + File.separator + "UI.ui_labels";
        return ResourceBundle.getBundle(baseName,locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
    }

    private void loadTranslationsFromFile(@NotNull Path path, Locale locale, Properties translations) {
        String[] parts = path.getFileName().toString().split("_");
        String baseName = "i18n" + File.separator + "labels." + parts[0] + "_" + parts[1];
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
            for (String key : bundle.keySet()) {
                translations.put(key, bundle.getString(key));
            }
        } catch (MissingResourceException e) {
            handleMissingResourceException(locale, translations);
        }
    }

    private void handleMissingResourceException(@NotNull Locale locale, Properties translations) {
        System.err.println("Unsupported language tag: " + locale.toLanguageTag());
        List<String> supportedLanguageTags = this.getSupportedLanguageTags();
        if (!supportedLanguageTags.isEmpty()) {
            String fallbackLanguageTag = supportedLanguageTags.get(0);
            System.err.println("Falling back to: " + fallbackLanguageTag);
            Locale fallbackLocale = Locale.forLanguageTag(fallbackLanguageTag);
            try {
                URL resource = this.getClass().getResource(LABELS_PATH);
                if (resource != null) {
                    Path path = Paths.get(resource.toURI());
                    Files.walk(path)
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".properties"))
                            .forEach(p -> loadTranslationsFromFile(p, fallbackLocale, translations));
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
