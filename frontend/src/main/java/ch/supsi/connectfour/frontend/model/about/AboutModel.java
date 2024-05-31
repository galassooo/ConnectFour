package ch.supsi.connectfour.frontend.model.about;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;
import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;

//OK
public class AboutModel implements IAboutModel {

    /* backend controllers */
    private static final TranslationsApplication translator;
    private static final PreferencesApplication preferences;

    /* data */
    private final String aboutConnectFourLabel;
    private final String builtOnLabel;
    private final String runtimeVersionLabel;
    private final String poweredByLabel;
    private final String closeText;
    private String developers;
    private String version;
    private String date;

    /* static initializer for static fields */
    static {
        translator = TranslationsApplication.getInstance();
        preferences = PreferencesApplication.getInstance();
    }

    /**
     * Construct the object
     */
    public AboutModel() {
        aboutConnectFourLabel = translator.translate("label.title");
        closeText = translator.translate("label.close");
        builtOnLabel = translator.translate("label.built_on");
        runtimeVersionLabel = translator.translate("label.runtime_version");
        poweredByLabel = translator.translate("label.powered_by");
        getProp();
    }

    /**
     * Retrieves the values for date, developers, and version dynamically from the POM file
     */
    private void getProp() {
        Properties properties = new Properties();

        //try to retrieve the properties file
        try (InputStream input = AboutModel.class.getClassLoader().getResourceAsStream("infoProperties/about.properties")) {
            if (input == null) {
                System.err.println("Sorry, unable to find app.properties");
                return;
            }
            //load properties
            properties.load(input);

            //save properties into fields
            version = properties.getProperty("app.version");
            developers = properties.getProperty("app.developers");
            formatDate(properties.getProperty("app.releaseDate"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a formatted string based on the current locale that represents a date
     *
     * @param dateString date in format dd/mm/yyyy
     */
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

    /* getters */
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
