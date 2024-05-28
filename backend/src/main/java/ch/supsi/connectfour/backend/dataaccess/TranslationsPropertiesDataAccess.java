package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
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
    private static final String LABELS_PATH = "i18n/labels";

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
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(LABELS_PATH);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("jar")) {
                    processJarResource(resource, locale, translations);
                } else {
                    processResource(resource, locale, translations);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return translations;
    }

    private void processResource(URL resource, Locale locale, Properties translations) {
        try {
            Path path = Paths.get(resource.toURI());
            Files.walk(path)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".properties"))
                    .forEach(p -> loadTranslationsFromFile(p, locale, translations));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResourceBundle getUIResourceBundle(Locale locale) {
        String baseName = UI_LABELS_PATH;
        return ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
    }

    private void loadTranslationsFromFile(@NotNull Path path, Locale locale, Properties translations) {
        String fileName = path.getFileName().toString();
        String[] parts = fileName.split("_");
        if (parts.length >= 2) {
            String baseName = String.format(LABELS_PATH_2, parts[0], parts[1]);
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
                loadTranslationsFromBundle(bundle, translations);
            } catch (MissingResourceException e) {
                handleMissingResourceException(locale, translations);
            }
        }
    }

    private void processJarResource(URL resource, Locale locale, Properties translations) {
        try {
            String decodedPath = URLDecoder.decode(resource.getPath(), StandardCharsets.UTF_8.name());
            String jarPath = decodedPath.substring(5, decodedPath.indexOf("!"));
            try (JarFile jarFile = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    if (entryName.startsWith(LABELS_PATH) && entryName.endsWith(".properties")) {
                        String[] parts = entryName.split("/");
                        if (parts.length >= 3) {
                            String[] lastParts = parts[2].split("_");
                            if (lastParts.length >= 2) {
                                String baseName = parts[0] + "." + parts[1] + "." + lastParts[0] + "_" + lastParts[1];
                                try {
                                    ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
                                    loadTranslationsFromBundle(bundle, translations);
                                } catch (MissingResourceException e) {
                                    System.err.println("Resource bundle not found: " + baseName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTranslationsFromBundle(ResourceBundle bundle, Properties translations) {
        for (String key : bundle.keySet()) {
            translations.put(key, bundle.getString(key));
        }
    }

    private void handleMissingResourceException(@NotNull Locale locale, Properties translations) {
        System.err.println("Unsupported language tag: " + locale.toLanguageTag());
        List<String> supportedLanguageTags = this.getSupportedLanguageTags();
        if (!supportedLanguageTags.isEmpty()) {
            String fallbackLanguageTag = supportedLanguageTags.get(0);
            System.err.println("Falling back to: " + fallbackLanguageTag);
            Locale fallbackLocale = Locale.forLanguageTag(fallbackLanguageTag);
            try {
                Enumeration<URL> resources = getClass().getClassLoader().getResources(LABELS_PATH);
                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();
                    if (resource.getProtocol().equals("jar")) {
                        processJarResource(resource, fallbackLocale, translations);
                    } else {
                        processResource(resource, fallbackLocale, translations);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
