package ch.supsi.connectfour.backend.application.translations;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public interface TranslationsBusinessInterface {

    boolean isSupportedLanguageTag(String languageTag);
    List<String> getSupportedLanguages();

    boolean changeLanguage(String languageTag);

    String translate(String key);

    ResourceBundle getUIResourceBundle(Locale locale);

}
