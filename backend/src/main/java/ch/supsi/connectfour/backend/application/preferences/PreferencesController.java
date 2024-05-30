package ch.supsi.connectfour.backend.application.preferences;

import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;

import java.util.Map;

public class PreferencesController {

    private static PreferencesController myself;

    private final PreferencesBusinessInterface preferencesModel;

    private PreferencesController() {
        this.preferencesModel = PreferencesModel.getInstance();
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }
        return myself;
    }

    /**
     * Return the value for the given key
     *
     * @param key
     * @return String
     */
    public Object getPreference(String key) {
        return this.preferencesModel.getPreference(key);
    }

    public void setPreference(Map.Entry<String, String> preference) {
        preferencesModel.setPreference(preference);
    }
}


