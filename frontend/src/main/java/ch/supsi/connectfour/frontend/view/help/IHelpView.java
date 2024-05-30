package ch.supsi.connectfour.frontend.view.help;

import javafx.event.ActionEvent;

import java.util.function.Consumer;

public interface IHelpView {
    void loadImage(String imagePath);

    void setHowToPlayLabel(String howToPlay);

    void setHelpLabel(String helpText);

    void removePreviousButton();

    void addPreviousButton();

    void setPreviousButtonText(String previousBtnText);

    void setPreviousButtonAction(Consumer<ActionEvent> previousBtnAction);

    void setNextButtonLabel(String nextBtnLabel);

    void setNextButtonAction(Consumer<ActionEvent> nextBtnAction);
}
