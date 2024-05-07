package ch.supsi.connectfour.backend.application.preferences;

public interface PreferencesBusinessInterface {

    String getCurrentLanguage();

    Object getPreference(String key);

}

