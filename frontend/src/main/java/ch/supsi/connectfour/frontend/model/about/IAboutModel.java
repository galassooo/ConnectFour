package ch.supsi.connectfour.frontend.model.about;

/**
 * Defines the behaviour that a generic about model should expose to the view and controller
 */
public interface IAboutModel {
    String getAboutConnectFourLabel();

    String getBuiltOnLabel();

    String getRuntimeVersionLabel();

    String getPoweredByLabel();

    String getCloseText();

    String getDevelopers();

    String getVersion();

    String getDate();
}