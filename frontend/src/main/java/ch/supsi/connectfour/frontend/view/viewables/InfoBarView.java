package ch.supsi.connectfour.frontend.view.viewables;

import ch.supsi.connectfour.backend.application.event.GameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

public class InfoBarView implements Viewable {

    /* field - default message */
    private static String defaultMessage;

    /* components */
    @FXML
    public Label infoBarText;
    @FXML
    private TextFlow textLabel;

    public static void setDefaultMessage(String message) {
        defaultMessage = message;
    }

    @FXML
    private void initialize() {
    }

    /* setters */
    private void setText(String text) {
        textLabel.getChildren().clear(); // se voglio pulire il precedente
        Label textNode = new Label(text);
        textLabel.getChildren().add(textNode);
    }

    /**
     * Displays the message associated with the event
     *
     * @param event event
     */
    @Override
    public void show(@NotNull GameEvent event) {
        setText(event.getEventMessage());
    }

    /**
     * clear the text box
     */
    @Override
    public void clear() {
        textLabel.getChildren().clear();
        textLabel.getChildren().add(new Label(defaultMessage)); //per evitare spostamento dei componenti
    }
}
