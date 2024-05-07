package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {

    private static final String translationsResourceBundlePath = "i18n.labels";

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
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle(
                    translationsResourceBundlePath,
                    locale,
                    ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT)
            );

        } catch (MissingResourceException mrex) {
            System.err.println("unsupported language tag..." + locale.toLanguageTag());

            List<String> supportedLanguageTags = this.getSupportedLanguageTags();
            String fallbackLanguageTag = supportedLanguageTags.get(0);
            System.err.println("falling back to..." + fallbackLanguageTag);

            bundle = ResourceBundle.getBundle(
                    translationsResourceBundlePath,
                    Locale.forLanguageTag(fallbackLanguageTag),
                    ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT)
            );
        }

        for (String key : bundle.keySet()) {
            translations.put(key, bundle.getString(key));
        }

        return translations;
    }

}


