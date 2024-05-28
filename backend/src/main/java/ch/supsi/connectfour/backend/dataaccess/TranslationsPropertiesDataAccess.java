package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.translations.TranslationsDataAccessInterface;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.util.ResourceBundle.Control.FORMAT_DEFAULT;

public class TranslationsPropertiesDataAccess implements TranslationsDataAccessInterface {
    // TODO: figure out why this explodes with File.separator
    //TODO: implementare separator lol
    private static final String LABELS_PATH = "/i18n/labels";
    private static final String SUPPORTED_LANGUAGES_PATH = "/supported-languages.properties";

    private static TranslationsPropertiesDataAccess myself;

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
        try (InputStream supportedLanguageTagsStream = this.getClass().getResourceAsStream(SUPPORTED_LANGUAGES_PATH)) {
            if (supportedLanguageTagsStream != null) {
                supportedLanguageTags.load(supportedLanguageTagsStream);
            }
        } catch (IOException ignored) {
            // Handle the exception appropriately if needed
        }
        return supportedLanguageTags;
    }

    @Override
    public List<String> getSupportedLanguageTags() {
        ArrayList<String> supportedLanguageTags = new ArrayList<>();
        Properties props = this.loadSupportedLanguageTags();
        for (Object key : props.keySet()) {
            supportedLanguageTags.add(props.getProperty((String) key));
        }
        return supportedLanguageTags;
    }

    @Override
    public Properties getTranslations(Locale locale) {
        final Properties translations = new Properties();
        try {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(LABELS_PATH);
            // TODO: remove while, there's only one loop ever
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("jar")) {
                    // connectfour-jar-with-dependencies.jar!/i18n/labels
                    // When we are working from a jar this method will always be called
                    processJarResource(resource, locale, translations);
                } else {
                    // If we are running the application from an IDE or not from a Jar
                    processResource(resource, locale, translations);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return translations;
    }

    private void processResource(URL resource, Locale locale, Properties translations) {
        File directory = new File(resource.getFile());

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String[] parts = file.getName().split("_");
            String baseName = String.format("%s.%s_%s", LABELS_PATH, parts[0], parts[1]);
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
            loadTranslationsFromBundle(bundle, translations);
        }
    }

    private void processJarResource(URL resource, Locale locale, Properties translations) throws IOException {
        // The path of the jar is something like jar:file:/../../../connectfour-jar-with-dependencies.jar![path of the resource]
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!")); // Getting the path of the jar
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                // Extracting each entry inside the jar
                JarEntry entry = entries.nextElement();
                // Extracting its name
                String entryName = entry.getName();
                if (entryName.startsWith(LABELS_PATH) && entryName.endsWith(".properties")) {
                    // ex: i18n/labels/preferences_labels_it_CH.properties
                    String[] parts = entryName.split("/");
                    // Handles the last part of the name e.g. preferences_labels_it_CH.properties
                    String[] lastParts = parts[2].split("_");
                    String baseName = parts[0] + "." + parts[1] + "." + (lastParts[0] + "_" + lastParts[1]);
                    System.out.println(baseName);
                    ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, ResourceBundle.Control.getNoFallbackControl(FORMAT_DEFAULT));
                    loadTranslationsFromBundle(bundle, translations);
                }
            }
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
                URL resource = this.getClass().getResource(LABELS_PATH);
                if (resource != null) {
                    Path path = Paths.get(resource.toURI());
                    Files.walk(path)
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".properties"))
                            .forEach(p -> loadTranslationsFromFile(p, fallbackLocale, translations));
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
