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

    //ALEX
    @Override
    public boolean isSupportedLanguageTag(String languageTag) {
        return this.supportedLanguageTags.contains(languageTag);
    }

    //ALEX
    @Override
    public List<String> getSupportedLanguages() {
        return List.copyOf(this.supportedLanguageTags); // Defensive copy
    }

    //ALEX
    @Override
    public boolean changeLanguage(String languageTag) {
        this.translations = translationsDao.getTranslations(Locale.forLanguageTag(languageTag));
        return this.translations != null;
    }


    //ALEX
    @Override
    public String translate(String key) {
        return this.translations.getProperty(key);
    }

    //ALEX
    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        return this.translationsDao.getUIResourceBundle(locale);
    }
}
