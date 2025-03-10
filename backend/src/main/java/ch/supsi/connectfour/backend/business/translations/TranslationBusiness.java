package ch.supsi.connectfour.backend.business.translations;

import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.TranslationsPropertiesDataAccess;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class TranslationBusiness implements TranslationsBusinessInterface {

    /* self reference */
    private static TranslationBusiness myself;

    /* data access reference */
    private final TranslationsDataAccessInterface translationsDao;

    /* fields */
    private final List<String> supportedLanguageTags;

    private Properties translations;

    /* constructor */
    protected TranslationBusiness() {
        this.translationsDao = TranslationsPropertiesDataAccess.getInstance();
        this.supportedLanguageTags = translationsDao.getSupportedLanguageTags();
    }

    public static TranslationBusiness getInstance() {
        if (myself == null) {
            myself = new TranslationBusiness();
        }
        return myself;
    }

    /**
     * Checks if a given language (provided in the form of a language tag) is supported
     *
     * @param languageTag the language tag associated with the language
     * @return whether the given language is supported
     */
    @Override
    public boolean isSupportedLanguageTag(String languageTag) {
        return this.supportedLanguageTags.contains(languageTag);
    }

    /**
     * Retrieves all supported languages of this application
     *
     * @return a list containing all supported languages
     */
    @Override
    public List<String> getSupportedLanguages() {
        return List.copyOf(this.supportedLanguageTags); // Defensive copy
    }

    /**
     * Changes the language of this application
     *
     * @param languageTag the language tag associated with the language to switch to
     * @return whether the language was successfully changed
     */
    @Override
    public boolean changeLanguage(String languageTag) {
        this.translations = translationsDao.getTranslations(Locale.forLanguageTag(languageTag));
        return this.translations != null;
    }


    /**
     * Retrieves the translation associated with the given key
     *
     * @param key the key associated with a key-value pair representing the translation
     * @return the translation associated with the given key (the value in the key-value pair)
     */
    @Override
    public String translate(String key) {
        return this.translations.getProperty(key);
    }

    /**
     * Return the UI translation bundle with the given locale
     *
     * @param locale selected locale
     * @return UI resource bundle
     */
    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        return this.translationsDao.getUIResourceBundle(locale);
    }
}
