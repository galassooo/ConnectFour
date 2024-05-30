package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;

public class ApplicationExitModel {
    private static ApplicationExitModel instance;
    private static final TranslationsController translations;

    /* Data used by the view */
    private final String message;
    private final String title;
    private final String confirmText;
    private final String cancelText;

    static {
        translations = TranslationsController.getInstance();
    }
    public static ApplicationExitModel getInstance(String message, String title, String confirmText, String cancelText) {
        if (instance == null) {
            instance = new ApplicationExitModel(message, title, confirmText, cancelText);
        }
        return instance;
    }

    private ApplicationExitModel(String message, String title, String confirmText, String cancelText) {
        this.message = message;
        this.title = title;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
    }


    public String translate(String s) {
        return translations.translate(s);
    }

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
