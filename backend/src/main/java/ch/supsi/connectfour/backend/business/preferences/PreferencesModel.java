package ch.supsi.connectfour.backend.business.preferences;


import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.dataaccess.PreferencesPropertiesDataAccess;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PreferencesModel implements PreferencesBusinessInterface {

    private static PreferencesModel myself;

    private final PreferencesDataAccessInterface preferencesDao;

    private final Properties userPreferences;
    private static final String PROPERTIES_FILE = "/user-preferences.properties";

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
        if(Objects.equals(value, "English"))
            value = "en-US";
        if(Objects.equals(value, "Italian"))
            value = "it-CH";

        Properties property = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)) {
            property.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Could not load existing properties file. A new one will be created.");
        }

        property.setProperty(key, value);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PROPERTIES_FILE);
            property.store(fileOutputStream, "");
            fileOutputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
