package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

//ALEX
public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {
    private static final String supportedLanguagesPath = "/supported-languages.properties";
    private static final String UI_LABELS_PATH = "i18n/UI/ui_labels";
    private static final String LABELS_PATH = "i18n/labels/";
    private static final String LABELS_FORMAT = ".properties";

    public static TranslationsPropertiesDataAccess myself;

    protected TranslationsPropertiesDataAccess() {
    }

    public static TranslationsPropertiesDataAccess getInstance() {
        if (myself == null) {
            myself = new TranslationsPropertiesDataAccess();
        }

        return myself;
    }

    private @NotNull Properties loadSupportedLanguageTags() {
        Properties supportedLanguageTags = new Properties();
        try (InputStream supportedLanguageTagsStream = this.getClass().getResourceAsStream(supportedLanguagesPath)) {
            if (supportedLanguageTagsStream != null) {
                supportedLanguageTags.load(supportedLanguageTagsStream);
            }
        } catch (IOException ignored) {
        }

        return supportedLanguageTags;
    }

    @Override
    public List<String> getSupportedLanguageTags() {
        List<String> supportedLanguageTags = new ArrayList<>();
        Properties props = this.loadSupportedLanguageTags();
        for (Object key : props.keySet()) {
            supportedLanguageTags.add(props.getProperty((String) key));
        }
        return supportedLanguageTags;
    }

    @Override
    public Properties getTranslations(Locale locale) {
        Properties translations = new Properties();

        List<ResourceBundle> bundles = getResourceBundlesForLocale(locale, LABELS_PATH);
        // It means it failed to load translations for the given locale, fallback to a default one
        if (bundles.isEmpty()) {
            Locale fallbackLocale = Locale.forLanguageTag(this.getSupportedLanguageTags().get(0));
            // This assumes that the pathToResources is valid, and the only thing that's not valid is the locale
            bundles = handleMissingResource(locale, fallbackLocale, LABELS_PATH);
        }
        bundles.forEach((b) -> {
            for (String key : b.keySet()) {
                translations.setProperty(key, b.getString(key));
            }
        });

        return translations;
    }

    private @NotNull List<ResourceBundle> getResourceBundlesForLocale(Locale locale, String pathToResources) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<ResourceBundle> resourceBundles = new ArrayList<>();
        try {
            Resource[] resources = resolver.getResources(String.format("classpath:%s*%s", pathToResources, LABELS_FORMAT));
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null && filename.contains("_" + locale.toString() + LABELS_FORMAT)) {
                    try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                        ResourceBundle resourceBundle = new PropertyResourceBundle(reader);
                        resourceBundles.add(resourceBundle);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceBundles;
    }

    private @NotNull List<ResourceBundle> handleMissingResource(@NotNull Locale invalidLocale, @NotNull Locale fallbackLocale, String pathToResources) {
        System.err.printf("Invalid locale: %s. Loading new locale: %s\n", invalidLocale, fallbackLocale);
        return getResourceBundlesForLocale(fallbackLocale, pathToResources);
    }

    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        List<ResourceBundle> bundles = getResourceBundlesForLocale(locale, UI_LABELS_PATH);
        if (bundles.isEmpty()) {
            Locale fallbackLocale = Locale.forLanguageTag(this.getSupportedLanguageTags().get(0));
            bundles = handleMissingResource(locale, fallbackLocale, UI_LABELS_PATH);
        }
        return bundles.get(0);
    }
}
