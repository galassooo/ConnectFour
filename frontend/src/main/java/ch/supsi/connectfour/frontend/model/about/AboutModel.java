package ch.supsi.connectfour.frontend.model.about;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;
import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
public class AboutModel implements IAboutModel {

    /* backend controllers */
    private static final PreferencesApplication preferences;

    /* data */

    private final HashMap<String, String> translatedLabels = new HashMap<>();
    private String developers;
    private String version;
    private String date;

    /* static initializer for static fields */
    static {
        preferences = PreferencesApplication.getInstance();
    }

    /**
     * Construct the object
     */
    public AboutModel() {
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
            System.err.println("Error while loading input properties for the about!");
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

    @Override
    public String getDevelopers() {
        return developers;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getTranslation(String key){
        return translatedLabels.get(key);
    }

    @Override
    public void translateAndSave() {
        List<String> list = List.of(
                "label.title", "label.close", "label.built_on", "label.runtime_version", "label.powered_by");
        TranslationsApplication translator = TranslationsApplication.getInstance();
        list.forEach( key -> translatedLabels.putIfAbsent(key, translator.translate(key)));
    }
}
