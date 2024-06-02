package ch.supsi.connectfour.frontend.model.preferences;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;
import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public class PreferencesModel implements IPreferencesModel {

    /* backend controller */
    private static PreferencesApplication backendController;

    /* data */

    private boolean languageOnlyRequested;

    private final HashMap<String, String> translatedLabels = new HashMap<>();

    /**
     * Construct an instance
     *
     */
    public PreferencesModel() {
        backendController = PreferencesApplication.getInstance();
        languageOnlyRequested = false;
    }

    /* getters */

    public boolean isOnlyLanguageRequested() {
        return languageOnlyRequested;
    }

    /* setter */
    public void setPreference(AbstractMap.SimpleEntry<String, String> preference) {
        backendController.setPreference(preference);
    }

    @Override
    public void setLanguageOnlyRequested(boolean languageOnlyRequested) {
        this.languageOnlyRequested = languageOnlyRequested;
    }


    @Override
    public String getTranslation(String key){
        return translatedLabels.get(key);
    }

    @Override
    public void translateAndSave() {
        List<String> list = List.of(
                "label.preferences_please_choose", "label.preferences_cannot_save", "label.preferences_language_only");
        TranslationsApplication translator = TranslationsApplication.getInstance();
        list.forEach( key -> translatedLabels.putIfAbsent(key, translator.translate(key)));
    }
}
