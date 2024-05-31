package ch.supsi.connectfour.frontend.model.exit;

//NOT OK
public class ApplicationExitModel implements IExitModel {

    /* self reference */
    private static ApplicationExitModel instance;

    /* Data used by the view */
    private final String message;
    private final String title;
    private final String confirmText;
    private final String cancelText;

    private ApplicationExitModel(String message, String title, String confirmText, String cancelText) {
        this.message = message;
        this.title = title;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
    }

    public static ApplicationExitModel getInstance(String message, String title, String confirmText, String cancelText) {
        if (instance == null) {
            instance = new ApplicationExitModel(message, title, confirmText, cancelText);
        }
        return instance;
    }

    /* getters */

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public String getCancelText() {
        return cancelText;
    }
}
