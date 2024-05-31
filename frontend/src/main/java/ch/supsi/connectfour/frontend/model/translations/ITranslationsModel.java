package ch.supsi.connectfour.frontend.model.translations;

public interface ITranslationsModel {
    Object getCurrentLanguage();

    String translate(String s);
}
