package ch.supsi.connectfour.backend.application.preferences;

import ch.supsi.connectfour.backend.business.preferences.PreferencesModel;

import java.util.Map;

public class PreferencesApplication {

    /* self reference */
    private static PreferencesApplication myself;

    /* business references */
    private final PreferencesBusinessInterface preferencesModel;


    private PreferencesApplication() {
        this.preferencesModel = PreferencesModel.getInstance();
    }

    public static PreferencesApplication getInstance() {
        if (myself == null) {
            myself = new PreferencesApplication();
        }
        return myself;
    }


    //ALEX
    public Object getPreference(String key) {
        return this.preferencesModel.getPreference(key);
    }

    //ALEX
    public void setPreference(Map.Entry<String, String> preference) {
        preferencesModel.setPreference(preference);
    }
}


