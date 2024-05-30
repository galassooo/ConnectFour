package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

//OK
public class HelpModel {
    /* backend controllers */
    private static final TranslationsApplication translations = TranslationsApplication.getInstance();

    /* popup title */
    private static final String title = translations.translate("label.help");

    /* data */
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
     * @param imagePath path to the image to be displayed
     * @param howToPlay screen title label
     * @param helpText help text
     * @param nextBtnLabel next button text
     * @param previousBtnText previous button text
     * @param previousBtnAction previous button action
     * @param nextBtnAction next button action
     * @param showPreviousBtn if true shows the previous button, otherwise no
     */
    public HelpModel(String imagePath, String howToPlay, String helpText, String nextBtnLabel, String previousBtnText, Consumer<ActionEvent> previousBtnAction, Consumer<ActionEvent> nextBtnAction, boolean showPreviousBtn) {
        this.imagePath = imagePath;
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
        return title;
    }

    public String getHowToPlay() {
        return howToPlay;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getNextBtnLabel() {
        return nextBtnLabel;
    }

    public String getHelpText() {
        return helpText;
    }

    public Consumer<ActionEvent> getPreviousBtnAction() {
        return previousBtnAction;
    }

    public @NotNull Consumer<ActionEvent> getNextBtnAction() {
        return nextBtnAction;
    }

    public String getPreviousBtnText() {
        return previousBtnText;
    }

    public boolean isShowPreviousBtn() {
        return showPreviousBtn;
    }
}
