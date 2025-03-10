package ch.supsi.connectfour.frontend.model.help;

import ch.supsi.connectfour.backend.application.preferences.PreferencesApplication;
import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class HelpModel implements IHelpModel {
    /* backend controllers */
    private final static TranslationsApplication translations = TranslationsApplication.getInstance();
    private final static PreferencesApplication preferencesApplication = PreferencesApplication.getInstance();

    /* popup title */
    private final static String TITLE = translations.translate("label.help");

    /* data */
    private final static String IMAGE_PATH_FORMAT = "/images/help/%s/%s";
    private final String imagePath;
    private final String howToPlay;
    private final String helpText;
    private final String nextBtnLabel;
    private final String previousBtnText;
    private final Consumer<ActionEvent> previousBtnAction;
    private final Consumer<ActionEvent> nextBtnAction;
    private final boolean showPreviousBtn;


    /**
     * Constructs the object using the backend controller for any translations
     *
     * @param imagePath         path to the image to be displayed
     * @param howToPlay         screen title label
     * @param helpText          help text
     * @param nextBtnLabel      next button text
     * @param previousBtnText   previous button text
     * @param previousBtnAction previous button action
     * @param nextBtnAction     next button action
     * @param showPreviousBtn   if true shows the previous button, otherwise no
     */
    public HelpModel(String imagePath, String howToPlay, String helpText, String nextBtnLabel, String previousBtnText, Consumer<ActionEvent> previousBtnAction, Consumer<ActionEvent> nextBtnAction, boolean showPreviousBtn) {
        String locale = (String) preferencesApplication.getPreference("language-tag");
        this.imagePath = String.format(IMAGE_PATH_FORMAT, locale, imagePath);
        this.howToPlay = translations.translate(howToPlay);
        this.helpText = translations.translate(helpText);
        this.nextBtnLabel = translations.translate(nextBtnLabel);
        this.previousBtnText = translations.translate(previousBtnText);
        this.previousBtnAction = previousBtnAction;
        this.nextBtnAction = nextBtnAction;
        this.showPreviousBtn = showPreviousBtn;
    }

    /* getters */
    public static String getTitle() {
        return TITLE;
    }

    @Override
    public String getHowToPlay() {
        return howToPlay;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String getNextBtnLabel() {
        return nextBtnLabel;
    }

    @Override
    public String getHelpText() {
        return helpText;
    }

    @Override
    public Consumer<ActionEvent> getPreviousBtnAction() {
        return previousBtnAction;
    }

    @Override
    public @NotNull Consumer<ActionEvent> getNextBtnAction() {
        return nextBtnAction;
    }

    @Override
    public String getPreviousBtnText() {
        return previousBtnText;
    }

    @Override
    public boolean isShowPreviousBtn() {
        return showPreviousBtn;
    }
}