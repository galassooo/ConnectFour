package ch.supsi.connectfour.frontend.view.about;

import javafx.event.ActionEvent;

import java.util.function.Consumer;
/**
 * Defines the behaviour that a generic about view should expose to the controller
 */
public interface IAboutView {
    void setOnActButton(Consumer<ActionEvent> eventConsumer);

}
