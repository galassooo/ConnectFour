package ch.supsi.connectfour.frontend.view.about;

import javafx.event.ActionEvent;

import java.util.function.Consumer;

public interface IAboutView {
    void setOnActButton(Consumer<ActionEvent> eventConsumer);

}
