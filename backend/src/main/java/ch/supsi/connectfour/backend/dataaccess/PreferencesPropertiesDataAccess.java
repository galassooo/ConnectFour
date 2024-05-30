package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.preferences.PreferencesDataAccessInterface;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

//ALEX tutta tu penso
public class PreferencesPropertiesDataAccess implements PreferencesDataAccessInterface {

    private static final String DEFAULT_USER_PREFERENCES_PROPERTIES = "/default-user-preferences.properties";

    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");

    private static final String PREFERENCES_DIRECTORY = ".connectfour";

    private static final String PREFERENCES_FILE = "/user-preferences.properties";

    public static PreferencesPropertiesDataAccess dao;

    private static Properties userPreferences;
    private Properties newProperties;

    // protected default constructor to avoid a new instance being requested from clients
    protected PreferencesPropertiesDataAccess() {
    }

    // singleton instantiation of this data access object
    // guarantees only a single instance exists in the life of the application
    public static PreferencesPropertiesDataAccess getInstance() {
        if (dao == null) {
            dao = new PreferencesPropertiesDataAccess();
        }

        return dao;
    }

    @Contract(pure = true)
    private @NotNull Path getUserPreferencesDirectoryPath() {
        return Path.of(USER_HOME_DIRECTORY, PREFERENCES_DIRECTORY);
    }

    private boolean userPreferencesDirectoryExists() {
        return Files.exists(this.getUserPreferencesDirectoryPath());
    }

    private @Nullable Path createUserPreferencesDirectory() {
        try {
            return Files.createDirectories(this.getUserPreferencesDirectoryPath());

        } catch (IOException ignoredForDemoPurposes) {
        }

        return null;
    }

    @Contract(pure = true)
    private @NotNull Path getUserPreferencesFilePath() {
        return Path.of(USER_HOME_DIRECTORY, PREFERENCES_DIRECTORY, PREFERENCES_FILE);
    }

    private boolean userPreferencesFileExists() {
        return Files.exists(this.getUserPreferencesFilePath());
    }

    private boolean createUserPreferencesFile(Properties defaultPreferences) {
        if (defaultPreferences == null) {
            return false;
        }

        if (!userPreferencesDirectoryExists()) {
            // user preferences directory does not exist
            // create it
            this.createUserPreferencesDirectory();
        }

        if (!userPreferencesFileExists()) {
            // user preferences file does not exist
            // create it
            try {
                // create user preferences file (with default preferences)
                FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
                defaultPreferences.store(outputStream, null);
                return true;

            } catch (IOException ignoredForDemoPurposes) {
                return false;
            }
        }

        return true;
    }

    private @NotNull Properties loadDefaultPreferences() {
        Properties defaultPreferences = new Properties();
        try {
            InputStream defaultPreferencesStream = this.getClass().getResourceAsStream(DEFAULT_USER_PREFERENCES_PROPERTIES);
            defaultPreferences.load(defaultPreferencesStream);

        } catch (IOException ignored) {
        }

        // return the properties object with the loaded preferences
        return defaultPreferences;
    }

    private @Nullable Properties loadPreferences(Path path) {
        Properties preferences = new Properties();
        try {
            preferences.load(new FileInputStream(String.valueOf(path)));

        } catch (IOException ignoredForDemoPurposes) {
            return null;
        }

        return preferences;
    }

    @Override
    public Properties getPreferences() {
        if (userPreferences != null) {
            return userPreferences;
        }

        if (userPreferencesFileExists()) {
            userPreferences = this.loadPreferences(this.getUserPreferencesFilePath());
            newProperties = (Properties) userPreferences.clone();
            return userPreferences;
        }

        userPreferences = this.loadDefaultPreferences();
        newProperties = (Properties) userPreferences.clone();
        boolean rv = this.createUserPreferencesFile(userPreferences);

        // return the properties object with the loaded preferences
        return userPreferences;
    }

    // This method works on a copy of the user preferences to only have an impact on the actual
    // file in the filesystem and the copy, so that the current settings are not affected until
    // an application restart
    @Override
    public boolean storePreference(Map.Entry<String, String> preference) {
        // Sets the specified preference
        newProperties.setProperty(preference.getKey(), preference.getValue());
        try {
            // Writes the newly edited preferences into the actual file in the filesystem
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
            newProperties.store(outputStream, null);
            return true;
        } catch (IOException ignoredForDemoPurposes) {
            return false;
        }
    }

}

