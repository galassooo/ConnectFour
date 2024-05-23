package ch.supsi.connectfour.backend.application.preferences;

import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;

public class PreferencesController {

    private static PreferencesController myself;

    private final PreferencesBusinessInterface preferencesModel;

    protected PreferencesController() {
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

    public void setPreference(String key, String value){
        preferencesModel.setPreference(key, value);
    }
}


