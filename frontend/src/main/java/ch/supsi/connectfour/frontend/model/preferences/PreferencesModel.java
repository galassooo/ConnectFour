package ch.supsi.connectfour.frontend.model.preferences;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;

import java.util.AbstractMap;

public class PreferencesModel implements IPreferencesModel {

    /* backend controller */
    private static PreferencesApplication backendController;

    /* data */
    private final String enableMessage;
    private final String disableMessage;
    private final String languageOnlyMessage;

    private boolean languageOnlyRequested;

    /**
     * Construct an instance
     *
     * @param enableMessage  message to be displayed for enable
     * @param disableMessage message to be displayed for disable
     */
    public PreferencesModel(String enableMessage, String disableMessage, String languageOnlyMessage) {
        backendController = PreferencesApplication.getInstance();
        this.enableMessage = enableMessage;
        this.disableMessage = disableMessage;
        this.languageOnlyMessage = languageOnlyMessage;
        languageOnlyRequested = false;
    }

    /* getters */
    @Override
    public String getEnableMessage() {
        return enableMessage;
    }

    @Override
    public String getDisableMessage() {
        return disableMessage;
    }

    @Override
    public String getLanguageOnlyMessage() {
        return languageOnlyMessage;
    }

    public boolean isOnlyLanguageRequested() {
        return languageOnlyRequested;
    }

    /* setter */
    public void setPreference(AbstractMap.SimpleEntry<String, String> preference) {
        backendController.setPreference(preference);
    }

    @Override
    public void setLanguageOnlyRequested(boolean languageOnlyRequested) {
        this.languageOnlyRequested = languageOnlyRequested;
    }
}
