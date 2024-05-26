package ch.supsi.connectfour.backend.application.translations;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.business.translations.TranslationsModel;
import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;

import java.util.List;

public class TranslationsController {

    private static TranslationsController myself;

    private final TranslationsBusinessInterface translationsModel;

    private final PreferencesBusinessInterface preferencesModel;

    protected TranslationsController() {
        this.preferencesModel = PreferencesModel.getInstance();
        this.translationsModel = TranslationsModel.getInstance();

        // TODO: fix once we implement the preferences!!!!
        String currentLanguage = preferencesModel.getCurrentLanguage();
        this.translationsModel.changeLanguage(currentLanguage);
    }

    public static TranslationsController getInstance() {
        if (myself == null) {
            myself = new TranslationsController();
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
     *
     * @return String
     */
    public String translate(String key) {
        return this.translationsModel.translate(key);
    }

}

