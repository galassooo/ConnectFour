package ch.supsi.connectfour.frontend.model.help;

import javafx.event.ActionEvent;

import java.util.function.Consumer;

/**
 * Defines the behaviour that a generic help model should expose to the view and controller
 */
public interface IHelpModel {
    String getImagePath();

    String getHowToPlay();

    String getHelpText();

    boolean isShowPreviousBtn();

    String getPreviousBtnText();

    Consumer<ActionEvent> getPreviousBtnAction();

    String getNextBtnLabel();

    Consumer<ActionEvent> getNextBtnAction();
}
