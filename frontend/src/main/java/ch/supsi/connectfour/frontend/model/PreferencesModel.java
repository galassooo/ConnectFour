package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;

import java.util.AbstractMap;
import java.util.Locale;

public class PreferencesModel {
    private static PreferencesModel instance;
    private static PreferencesController backendController;

    private String enableMessage;
    private String disableMessage;

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
