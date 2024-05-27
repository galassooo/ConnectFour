package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {
    // TODO: figure out why this explodes with File.separator
    private static final String LABELS_PATH = "/i18n/labels";

    private static final String supportedLanguagesPath = "/supported-languages.properties";

    public static TranslationsPropertiesDataAccess myself;

    protected TranslationsPropertiesDataAccess() {}

    // singleton instantiation of this data access object
    // guarantees only a single instance exists in the life of the application
    public static TranslationsPropertiesDataAccess getInstance() {
        if (myself == null) {
            myself = new TranslationsPropertiesDataAccess();
        }

        return myself;
    }

    private Properties loadSupportedLanguageTags() {
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
        for (Object key: props.keySet()) {
            supportedLanguageTags.add(props.getProperty((String)key));
        }

        // return
        return supportedLanguageTags;
    }

    @Override
    public Properties getTranslations(Locale locale) {
        final Properties translations = new Properties();

        try {
            // TODO: check if this works in a jar!!!!
            Files.walk(Paths.get(Objects.requireNonNull(this.getClass().getResource(LABELS_PATH)).toURI()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".properties")) // TODO: could avoid this check as there will not be any other files in there
                    .forEach(path -> {
                        try {
                            // Extract the base name and locale from the file path
                            String[] parts = path.getFileName().toString().split("_");

                            // TODO: fix hardcoding of prefix
                            String baseName = "i18n\\labels." + parts[0] + "_" + path.getParent().getFileName().toString();

                            // The ResourceBundle.Control allows to prevent any fallback mechanisms in the ResourceBundle factory methods, which is what we want because we want to manually handle invalid locales
                            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
                            for (String key : bundle.keySet()) {
                                translations.put(key, bundle.getString(key));
                            }

                        } catch (MissingResourceException e) {
                            System.err.println("unsupported language tag..." + locale.toLanguageTag());

                            List<String> supportedLanguageTags = this.getSupportedLanguageTags();
                            String fallbackLanguageTag = supportedLanguageTags.get(0);
                            System.err.println("falling back to..." + fallbackLanguageTag);

                            try {
                                Files.walk(Paths.get(Objects.requireNonNull(this.getClass().getResource(LABELS_PATH)).toURI()))
                                        .filter(Files::isRegularFile)
                                        .filter(p -> p.toString().endsWith(".properties"))
                                        .forEach(p -> {
                                            // Extract the base name and locale from the file path
                                            String[] parts = p.getFileName().toString().split("_");

                                            // TODO: fix hardcoding of prefix
                                            String baseName = "i18n\\labels." + parts[0] + "_" + p.getParent().getFileName().toString();

                                            ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.forLanguageTag(fallbackLanguageTag), ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
                                            for (String key : bundle.keySet()) {
                                                translations.put(key, bundle.getString(key));
                                            }
                                        });
                            } catch (IOException | URISyntaxException ex) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (IOException e) {
            // TODO: do something clever with this exception thrown by Files.walk
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // todo: do something clever
            throw new RuntimeException(e);
        }
        return translations;
    }

}


