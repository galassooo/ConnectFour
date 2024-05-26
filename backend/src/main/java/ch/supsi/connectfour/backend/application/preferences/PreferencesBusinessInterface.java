package ch.supsi.connectfour.backend.application.preferences;

import java.util.Map;

public interface PreferencesBusinessInterface {

    String getCurrentLanguage();

    Object getPreference(String key);

    void setPreference(Map.Entry<String, String> preference);

}

