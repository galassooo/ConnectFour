package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;

public class ApplicationExitModel {
    private static ApplicationExitModel instance;
    private static final TranslationsController translations;

    static {
        translations = TranslationsController.getInstance();
    }
    public static ApplicationExitModel getInstance() {
        if (instance == null) {
            instance = new ApplicationExitModel();
        }
        return instance;
    }

    private ApplicationExitModel() {}


    public String translate(String s) {
        return translations.translate(s);
    }
}
