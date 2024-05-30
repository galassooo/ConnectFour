package ch.supsi.connectfour.backend.application.translations;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;
import ch.supsi.connectfour.backend.business.translations.TranslationBusiness;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationsApplication {

    private static TranslationsApplication myself;

    private final TranslationsBusinessInterface translationsModel;

    private final PreferencesBusinessInterface preferencesModel;

    private TranslationsApplication() {
        this.preferencesModel = PreferencesModel.getInstance();
        this.translationsModel = TranslationBusiness.getInstance();

        String currentLanguage = preferencesModel.getCurrentLanguage();
        this.translationsModel.changeLanguage(currentLanguage);
    }

    public static TranslationsApplication getInstance() {
        if (myself == null) {
            myself = new TranslationsApplication();
        }

        return myself;
    }

    public List<String> getSupportedLanguages() {
        return this.translationsModel.getSupportedLanguages();
    }

    /**
     * Translate the given key
     *
     * @param key
     * @return String
     */
    public String translate(String key) {
        return this.translationsModel.translate(key);
    }

    public ResourceBundle getTranslationBundle(Locale locale) {
        return this.translationsModel.getUIResourceBundle(locale);
    }
}

