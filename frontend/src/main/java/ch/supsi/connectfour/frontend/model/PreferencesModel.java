package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;

import java.util.AbstractMap;

//OK
public class PreferencesModel {

    /* backend controller */
    private static PreferencesApplication backendController;

    /* data */
    private final String enableMessage;
    private final String disableMessage;
    private final String languageOnlyMessage;

    private boolean languageOnlyRequested;

    /**
     * construct an instance
     * @param enableMessage message to be displayed for enable
     * @param disableMessage message to be displayed for disable
     */
    public PreferencesModel(String enableMessage, String disableMessage, String languageOnlyMessage) {
        backendController = PreferencesApplication.getInstance();
        this.enableMessage = enableMessage;
        this.disableMessage = disableMessage;
        this.languageOnlyMessage = languageOnlyMessage;
        languageOnlyRequested = false;
    }

    /* setter */
    public void setPreference(AbstractMap.SimpleEntry<String, String> preference) {
        backendController.setPreference(preference);
    }

    /* getters */
    public String getEnableMessage() {
        return enableMessage;
    }

    public String getDisableMessage() {
        return disableMessage;
    }

    public String getLanguageOnlyMessage() {
        return languageOnlyMessage;
    }

    public boolean isLanguageOnlyRequested() {
        return languageOnlyRequested;
    }

    public void setLanguageOnlyRequested(boolean languageOnlyRequested) {
        this.languageOnlyRequested = languageOnlyRequested;
    }
}
