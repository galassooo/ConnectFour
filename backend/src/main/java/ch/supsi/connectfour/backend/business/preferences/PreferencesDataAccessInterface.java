package ch.supsi.connectfour.backend.business.preferences;

import java.util.Map;
import java.util.Properties;

public interface PreferencesDataAccessInterface {

    Properties getPreferences();

    boolean storePreference(Map.Entry<String, String> preference);
}