package ch.supsi.connectfour.backend.application.preferences;

import java.util.Map;

/*
    Defines the behaviour that a generic preferences model should expose to the controller
 */
public interface PreferencesBusinessInterface {

    String getCurrentLanguage();

    Object getPreference(String key);

    void setPreference(Map.Entry<String, String> preference);
}

