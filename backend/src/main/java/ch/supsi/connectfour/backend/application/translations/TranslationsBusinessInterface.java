package ch.supsi.connectfour.backend.application.translations;

public interface TranslationsBusinessInterface {

    boolean isSupportedLanguageTag(String languageTag);

    boolean changeLanguage(String languageTag);

    String translate(String key);

}
