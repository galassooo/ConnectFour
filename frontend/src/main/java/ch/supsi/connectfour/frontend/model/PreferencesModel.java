package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;

import java.util.AbstractMap;

//OK
public class PreferencesModel {

    /* backend controller */
    private static PreferencesController backendController;

    /* data */
    private final String enableMessage;
    private final String disableMessage;


    /**
     * construct an instance
     * @param enableMessage message to be displayed for enable
     * @param disableMessage message to be displayed for disable
     */
    public PreferencesModel(String enableMessage, String disableMessage) {
        backendController = PreferencesController.getInstance();
        this.enableMessage = enableMessage;
        this.disableMessage = disableMessage;
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
}
