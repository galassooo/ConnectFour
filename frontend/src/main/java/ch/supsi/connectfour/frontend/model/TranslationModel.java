package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;

import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationModel {
    private static TranslationModel instance;
    private final TranslationsController translationsController;
    private final PreferencesController preferencesController;

    private ResourceBundle uiBundle;
    private Locale locale;

    public static TranslationModel getInstance() {
        if (instance == null) {
            instance = new TranslationModel();
        }
        return instance;
    }

    private TranslationModel() {
        translationsController = TranslationsController.getInstance();
        preferencesController = PreferencesController.getInstance();
    }

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
}
