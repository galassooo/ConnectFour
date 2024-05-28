package ch.supsi.connectfour.backend.business.translations;

import ch.supsi.connectfour.backend.application.translations.TranslationsBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.TranslationsPropertiesDataAccess;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class TranslationsModel implements TranslationsBusinessInterface {

    private static TranslationsModel myself;

    private final TranslationsDataAccessInterface translationsDao;

    private final List<String> supportedLanguageTags;

    private Properties translations;

    protected TranslationsModel() {
        this.translationsDao = TranslationsPropertiesDataAccess.getInstance();
        this.supportedLanguageTags = translationsDao.getSupportedLanguageTags();
    }

    public static TranslationsModel getInstance() {
        if (myself == null) {
            myself = new TranslationsModel();
        }
        return myself;
    }

    @Override
    public boolean isSupportedLanguageTag(String languageTag) {
        return this.supportedLanguageTags.contains(languageTag);
    }

    @Override
    public List<String> getSupportedLanguages() {
        return List.copyOf(this.supportedLanguageTags); // Defensive copy
    }

    @Override
    public boolean changeLanguage(String languageTag) {
        this.translations = translationsDao.getTranslations(Locale.forLanguageTag(languageTag));
        return this.translations != null;
    }


    @Override
    public String translate(String key) {
        return this.translations.getProperty(key);
    }

    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        return this.translationsDao.getUIResourceBundle(locale);
    }
}
