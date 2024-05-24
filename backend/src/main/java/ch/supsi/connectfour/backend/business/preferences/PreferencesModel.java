package ch.supsi.connectfour.backend.business.preferences;


import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.PreferencesPropertiesDataAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PreferencesModel implements PreferencesBusinessInterface {

    private static PreferencesModel myself;

    private final PreferencesDataAccessInterface preferencesDao;

    private final Properties userPreferences;
    private static final String PROPERTIES_FILE = "src/main/resources/user-preferences.properties";

    protected PreferencesModel() {
        this.preferencesDao = PreferencesPropertiesDataAccess.getInstance();
        this.userPreferences = preferencesDao.getPreferences();
    }

    public static PreferencesModel getInstance() {
        if (myself == null) {
            myself = new PreferencesModel();
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
    public void setPreference(String key, String value) {
        Properties property = preferencesDao.getPreferences();

        property.setProperty(key, value);
        this.preferencesDao.storePreferences(property);
    }
}
