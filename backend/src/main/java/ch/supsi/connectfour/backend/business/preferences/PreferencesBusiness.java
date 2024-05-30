package ch.supsi.connectfour.backend.business.preferences;


import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.PreferencesPropertiesDataAccess;

import java.util.Map;
import java.util.Properties;

public class PreferencesBusiness implements PreferencesBusinessInterface {

    /* self reference */
    private static PreferencesBusiness myself;

    /* data access reference */
    private final PreferencesDataAccessInterface preferencesDao;

    /* field */
    private final Properties userPreferences;

    /* constructor */
    protected PreferencesBusiness() {
        this.preferencesDao = PreferencesPropertiesDataAccess.getInstance();
        this.userPreferences = preferencesDao.getPreferences();
    }

    public static PreferencesBusiness getInstance() {
        if (myself == null) {
            myself = new PreferencesBusiness();
        }

        return myself;
    }

    //ALEX
    @Override
    public String getCurrentLanguage() {
        return userPreferences.getProperty("language-tag");
    }

    //ALEX
    @Override
    public Object getPreference(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        if (userPreferences == null) {
            return null;
        }

        return userPreferences.get(key);
    }

    //ALEX
    @Override
    public void setPreference(Map.Entry<String, String> preference) {
        this.preferencesDao.storePreference(preference);
    }
}
