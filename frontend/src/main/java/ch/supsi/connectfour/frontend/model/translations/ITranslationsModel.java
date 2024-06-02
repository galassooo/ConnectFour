package ch.supsi.connectfour.frontend.model.translations;

/**
 * Defines the behaviour that a generic translations model should expose to the view and controller
 */
public interface ITranslationsModel {
    Object getCurrentLanguage();

    String translate(String s);
}