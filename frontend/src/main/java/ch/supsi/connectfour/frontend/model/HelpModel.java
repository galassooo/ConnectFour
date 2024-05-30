package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import javafx.event.ActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class HelpModel {
    private static final TranslationsController translations = TranslationsController.getInstance();
    private static final String title = translations.translate("label.help");
    private final String imagePath;
    private final String howToPlay;
    private final String helpText;
    private final String nextBtnLabel;
    private final String previousBtnText;
    private final Consumer<ActionEvent> previousBtnAction;
    private final Consumer<ActionEvent> nextBtnAction;

    private final boolean showPreviousBtn;


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
