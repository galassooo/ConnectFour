package ch.supsi.connectfour.frontend.model.preferences;

/**
 * Defines the behaviour that a generic preferences model should expose to the view and controller
 */
public interface IPreferencesModel {
    String getLanguageOnlyMessage();

    void setLanguageOnlyRequested(boolean b);

    String getDisableMessage();

    String getEnableMessage();
}