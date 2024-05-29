package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {
    private static final String LABELS_PATH = String.format("i18n%slabels%s", File.separator, File.separator);

    private static final String supportedLanguagesPath = "/supported-languages.properties";
    private static final String UI_LABELS_PATH = "i18n/UI/ui_labels";
    private static final String LABELS_PATH_2 = "i18n/labels.%s_%s";

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

        List<ResourceBundle> bundles = getResourceBundlesForLocale(locale);
        // It means it failed to load translations for the given locale, fallback to a default one
        if (bundles.isEmpty()) {
            Locale fallbackLocale = Locale.forLanguageTag(this.getSupportedLanguageTags().get(0));
            System.err.printf("Invalid locale: %s. Loading new locale: %s\n", locale.toString(), fallbackLocale.toString());
            bundles = getResourceBundlesForLocale(fallbackLocale);
        }
        bundles.forEach((b) -> {
            for (String key : b.keySet()) {
                translations.setProperty(key, b.getString(key));
            }
        });

        return translations;
    }
    private List<ResourceBundle> getResourceBundlesForLocale(Locale locale) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<ResourceBundle> resourceBundles = new ArrayList<>();
        try {
            Resource[] resources = resolver.getResources(String.format("classpath:%s*.properties", "i18n/labels/"));
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null && filename.contains("_" + locale.toString() + ".properties")) {
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

    // TODO: fix this. It does not work if the locale is not valid. Probably better to make the method above more generic so that same one can be used for these
    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        String baseName = UI_LABELS_PATH;
        return ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
    }
}
