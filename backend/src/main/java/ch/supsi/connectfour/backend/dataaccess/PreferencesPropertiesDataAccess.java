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

public class PreferencesPropertiesDataAccess implements PreferencesDataAccessInterface {

    /* where the default user preferences are located */
    private static final String DEFAULT_USER_PREFERENCES_PROPERTIES = "/default-user-preferences.properties";
    /* the user home directory */
    private static final String USER_HOME_DIRECTORY = System.getProperty("user.home");
    /* the default directory that will be created on the user's filesystem */
    private static final String PREFERENCES_DIRECTORY = ".connectfour";
    /* the name of the preferences file that will be created on the user's filesystem */
    private static final String PREFERENCES_FILE = "/user-preferences.properties";
    /* self reference */
    public static PreferencesPropertiesDataAccess dao;
    /* an object representation of the user preferences in the filesystem */
    private static Properties userPreferences;
    /* an object representation of the user preferences modified at runtime */
    private Properties newProperties;

    // Protected default constructor to avoid a new instance being requested from clients
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

    /**
     * Builds the path associated with the preferences directory in the user's filesystem
     *
     * @return a path associated with the preferences directory in the user's filesystem
     */
    @Contract(pure = true)
    private @NotNull Path getUserPreferencesDirectoryPath() {
        return Path.of(USER_HOME_DIRECTORY, PREFERENCES_DIRECTORY);
    }


    private boolean userPreferencesDirectoryExists() {
        return Files.exists(this.getUserPreferencesDirectoryPath());
    }

    /**
     * Tries to create the user preferences directory
     *
     * @return a path to the user preferences directory, or null if an exception was thrown
     */
    private @Nullable Path createUserPreferencesDirectory() {
        try {
            return Files.createDirectories(this.getUserPreferencesDirectoryPath());
        } catch (IOException e) {
            System.err.printf("Unable to create user preferences directory: %s\n", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Builds the path associated with the preferences file in the user's filesystem
     *
     * @return a path associated with the preferences file in the user's filesystem
     */
    @Contract(pure = true)
    private @NotNull Path getUserPreferencesFilePath() {
        return Path.of(USER_HOME_DIRECTORY, PREFERENCES_DIRECTORY, PREFERENCES_FILE);
    }

    // Whether the user preferences file exists
    private boolean userPreferencesFileExists() {
        return Files.exists(this.getUserPreferencesFilePath());
    }

    /**
     * Tries to create a user preferences file in the users filesystem
     *
     * @param defaultPreferences a properties file representing the default preferences
     * @return true if it created the file, false otherwise
     */
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

            } catch (IOException e) {
                System.err.printf("Unable to create user preferences file: %s\n", getUserPreferencesFilePath());
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * Tries loading the default user preferences
     *
     * @return a properties object representing the default preferences
     */
    private @NotNull Properties loadDefaultPreferences() {
        Properties defaultPreferences = new Properties();
        try {
            InputStream defaultPreferencesStream = this.getClass().getResourceAsStream(DEFAULT_USER_PREFERENCES_PROPERTIES);
            defaultPreferences.load(defaultPreferencesStream);

        } catch (IOException e) {
            System.err.printf("Unable to load user preferences file: %s\n", getUserPreferencesFilePath());
            e.printStackTrace();
        }

        // return the properties object with the loaded preferences
        return defaultPreferences;
    }

    /**
     * Loads and returns the preferences stored in the preferences file
     *
     * @param path the path to the preferences file
     * @return a properties file representing the preferences, null if an exception was thrown
     */
    private @Nullable Properties loadPreferences(Path path) {
        Properties preferences = new Properties();
        try {
            preferences.load(new FileInputStream(String.valueOf(path)));

        } catch (IOException e) {
            System.err.printf("Unable to load user preferences file from the specified path: %s\n", path.toString());
            e.printStackTrace();
            return null;
        }

        return preferences;
    }

    /**
     * Retrieves the properties object associated with the user's preferences. If no preferences file
     * exists yet, a new one is created.
     *
     * @return a properties object representing the user preferences
     */
    @Override
    public Properties getPreferences() {
        if (userPreferences != null) {
            return userPreferences;
        }

        if (userPreferencesFileExists()) {
            userPreferences = this.loadPreferences(this.getUserPreferencesFilePath());
            // the user preferences exist if we're here..
            newProperties = (Properties) userPreferences.clone();
            return userPreferences;
        }

        userPreferences = this.loadDefaultPreferences();
        newProperties = (Properties) userPreferences.clone();
        boolean rv = this.createUserPreferencesFile(userPreferences);

        // return the properties object with the loaded preferences
        return userPreferences;
    }

    /**
     * Stores the preference in the user preferences. This method works on a copy of the user preferences
     * stored in newProperties to only have an impact on the actual properties file in the filesystem and the copy
     * so that the current settings are not affected until an application restart
     *
     * @param preference the key-value pair representing the preference to be stored
     * @return true if the operation succeeded, false otherwise
     */
    @Override
    public boolean storePreference(Map.@NotNull Entry<String, String> preference) {
        // Sets the specified preference
        newProperties.setProperty(preference.getKey(), preference.getValue());
        try {
            // Writes the newly edited preferences into the actual file in the filesystem
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
            newProperties.store(outputStream, null);
            return true;
        } catch (IOException e) {
            System.err.printf("Unable to store preference %s to file: %s\n", preference, getUserPreferencesFilePath());
            return false;
        }
    }
}

