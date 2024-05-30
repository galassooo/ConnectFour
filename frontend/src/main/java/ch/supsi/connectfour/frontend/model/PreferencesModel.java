package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;

import java.util.AbstractMap;

public class PreferencesModel {
    private static PreferencesModel instance;
    private static PreferencesController backendController;

    private final String enableMessage;
    private final String disableMessage;

    public PreferencesModel(String enableMessage, String disableMessage) {
        backendController = PreferencesController.getInstance();
        this.enableMessage = enableMessage;
        this.disableMessage = disableMessage;
    }

    public void setPreference(AbstractMap.SimpleEntry<String, String> preference) {
        backendController.setPreference(preference);
    }

    public String getEnableMessage() {
        return enableMessage;
    }

    public String getDisableMessage() {
        return disableMessage;
    }
}
