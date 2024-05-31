package ch.supsi.connectfour.frontend.model.exit;
/**
 * Defines the behaviour that a generic exit model should expose to the view and controller
 */
public interface IExitModel {
    String getTitle();

    String getMessage();

    String getConfirmText();

    String getCancelText();
}
