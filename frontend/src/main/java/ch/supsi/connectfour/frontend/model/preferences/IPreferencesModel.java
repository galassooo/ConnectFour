package ch.supsi.connectfour.frontend.model.preferences;

public interface IPreferencesModel {
    String getLanguageOnlyMessage();

    void setLanguageOnlyRequested(boolean b);

    String getDisableMessage();

    String getEnableMessage();
}
