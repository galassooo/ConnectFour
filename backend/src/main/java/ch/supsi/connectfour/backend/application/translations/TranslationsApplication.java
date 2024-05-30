package ch.supsi.connectfour.backend.application.translations;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;
import ch.supsi.connectfour.backend.business.translations.TranslationBusiness;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationsApplication {

    /* self reference */
    private static TranslationsApplication myself;

    /* business references */
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

    //ALEX
    public List<String> getSupportedLanguages() {
        return this.translationsModel.getSupportedLanguages();
    }


    //ALEX
    public String translate(String key) {
        return this.translationsModel.translate(key);
    }

    /**
     * return the UI translation bundle with the given locale
     * @param locale selected locale
     * @return UI resource bundle
     */
    public ResourceBundle getTranslationBundle(Locale locale) {
        return this.translationsModel.getUIResourceBundle(locale);
    }
}

