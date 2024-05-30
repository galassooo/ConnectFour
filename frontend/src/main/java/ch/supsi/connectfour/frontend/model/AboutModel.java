package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.preferences.PreferencesController;
import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;

public class AboutModel {

    private static final TranslationsController translator;
    private static final PreferencesController preferences;

    private final String aboutConnectFourLabel;

    private final String builtOnLabel;

    private final String runtimeVersionLabel;

    private final String poweredByLabel;
    private final String closeText;

    private String developers;
    private String version;
    private String date;

    static {
        translator = TranslationsController.getInstance();
        preferences = PreferencesController.getInstance();
    }

    public AboutModel(){
        aboutConnectFourLabel = translator.translate("label.title");
        closeText = translator.translate("label.close");
        builtOnLabel = translator.translate("label.built_on");
        runtimeVersionLabel = translator.translate("label.runtime_version");
        poweredByLabel = translator.translate("label.powered_by");
        getProp();
    }

    private void getProp(){
        Properties properties = new Properties();
        try (InputStream input = AboutModel.class.getClassLoader().getResourceAsStream("infoProperties/about.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find app.properties");
                return;
            }
            properties.load(input);
            version =  properties.getProperty("app.version");
            developers = properties.getProperty("app.developers");
            formatDate(properties.getProperty("app.releaseDate"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void formatDate(String dateString) {
        //create a formatter based on the label in the pom
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //parse the date into a LocalDate obj
        LocalDate date = LocalDate.parse(dateString, inputFormatter);

        //retrieve current language
        String localeString = (String) preferences.getPreference("language-tag");

        //create locale from current language
        Locale locale = Locale.forLanguageTag(localeString);

        //uses a default java formatter based on the locale obj
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale);

        //format the date and save it into date field
        this.date = date.format(formatter);

    }

    public String getAboutConnectFourLabel() {
        return aboutConnectFourLabel;
    }

    public String getBuiltOnLabel() {
        return builtOnLabel;
    }

    public String getRuntimeVersionLabel() {
        return runtimeVersionLabel;
    }

    public String getPoweredByLabel() {
        return poweredByLabel;
    }

    public String getCloseText() {
        return closeText;
    }

    public String getDevelopers() {
        return developers;
    }

    public String getVersion() {
        return version;
    }

    public String getDate() {
        return date;
    }
}
