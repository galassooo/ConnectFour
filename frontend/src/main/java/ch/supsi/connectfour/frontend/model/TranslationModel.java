package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationModel {
    private static TranslationModel instance;
    private static final TranslationsController translationsController;
    private static final PreferencesController preferencesController;

    private ResourceBundle uiBundle;
    private Locale locale;

    static {
        translationsController = TranslationsController.getInstance();
        preferencesController = PreferencesController.getInstance();
    }

    public static TranslationModel getInstance() {
        if (instance == null) {
            instance = new TranslationModel();
        }
        return instance;
    }

    private TranslationModel() {}

    public ResourceBundle getUiBundle() {
        if (uiBundle == null) { //load only once
            uiBundle = translationsController.getTranslationBundle(getLocale());
        }
        return uiBundle;
    }

    private Locale getLocale() {
        if (locale == null) {
            locale = Locale.forLanguageTag(preferencesController.getPreference("language-tag").toString());
        }
        return locale;
    }

    public String translate(String s) {
        return translationsController.translate(s);
    }

    public List<String> getSupportedLanguages() {
        return translationsController.getSupportedLanguages();
    }
    public Locale getCurrentLanguage() {
        return Locale.forLanguageTag(String.valueOf(preferencesController.getPreference("language-tag")));
    }
}
