package ch.supsi.connectfour.backend.business.preferences;


import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.PreferencesPropertiesDataAccess;

import java.util.Map;
import java.util.Properties;

public class PreferencesBusiness implements PreferencesBusinessInterface {

    private static PreferencesBusiness myself;

    private final PreferencesDataAccessInterface preferencesDao;

    private final Properties userPreferences;

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

    @Override
    public String getCurrentLanguage() {
        return userPreferences.getProperty("language-tag");
    }

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

    @Override
    public void setPreference(Map.Entry<String, String> preference) {
        this.preferencesDao.storePreference(preference);
    }
}
