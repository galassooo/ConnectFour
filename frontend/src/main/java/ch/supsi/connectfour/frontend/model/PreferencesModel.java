package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;

import java.util.AbstractMap;
import java.util.Locale;

public class PreferencesModel {
    private static PreferencesModel instance;
    private static PreferencesController backendController;

    public static PreferencesModel getInstance() {
        if (instance == null) {
            instance = new PreferencesModel();
        }
        return instance;
    }

    private PreferencesModel() {
        backendController = PreferencesController.getInstance();
    }

    public void setPreference(AbstractMap.SimpleEntry<String, String> preference) {
        backendController.setPreference(preference);
    }
    public Locale getLanguage() {
        return Locale.forLanguageTag(String.valueOf(backendController.getPreference("language-tag")));
    }
}
