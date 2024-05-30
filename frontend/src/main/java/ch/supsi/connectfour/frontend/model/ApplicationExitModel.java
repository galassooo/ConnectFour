package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;

public class ApplicationExitModel {
    private static final TranslationsController translations;

    static {
        translations = TranslationsController.getInstance();
    }

    public ApplicationExitModel() {}


    public String translate(String s) {
        return translations.translate(s);
    }
}
