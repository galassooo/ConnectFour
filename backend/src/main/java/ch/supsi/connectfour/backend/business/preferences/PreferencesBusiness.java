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

    /**
     * Delegates the retrieval of the current language to the data access layer
     *
     * @return the current language of the program
     */
    @Override
    public String getCurrentLanguage() {
        return userPreferences.getProperty("language-tag");
    }

    /**
     * Delegates the retrieval of the preference associated with a given key
     *
     * @param key the key associated with the preference in a key-value pair
     * @return the value associated with the given key
     */
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

    /**
     * Delegates the storage of the key-value pair associated with a preference to the data access layer
     *
     * @param preference the key-value pair representing the preference to be stored
     */
    @Override
    public void setPreference(Map.Entry<String, String> preference) {
        this.preferencesDao.storePreference(preference);
    }
}
