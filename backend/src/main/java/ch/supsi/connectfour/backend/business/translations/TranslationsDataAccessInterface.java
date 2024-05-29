package ch.supsi.connectfour.backend.business.translations;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public interface TranslationsDataAccessInterface {

    List<String> getSupportedLanguageTags();

    Properties getTranslations(Locale locale);
    ResourceBundle getUIResourceBundle(Locale locale);
}
