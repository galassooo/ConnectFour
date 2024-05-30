package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;

//NOT OK
public class ApplicationExitModel {

    /* self reference */
    private static ApplicationExitModel instance;

    /* backend controller*/

    //NON VIENE MAI USATO... RIMUOVERE? (vedi controller)
    private static final TranslationsApplication translations;

    static {
        translations = TranslationsApplication.getInstance();
    }

    /* Data used by the view */
    private final String message;
    private final String title;
    private final String confirmText;
    private final String cancelText;

    //ALEX
    private ApplicationExitModel(String message, String title, String confirmText, String cancelText) {
        this.message = message;
        this.title = title;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
    }

    //ALEX
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
