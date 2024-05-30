package ch.supsi.connectfour.frontend.view.viewables;

import ch.supsi.connectfour.backend.application.event.GameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;

public class InfoBarView implements Viewable {
    private static String defaultMessage;
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

    private void setText(String text) {
        textLabel.getChildren().clear(); // se voglio pulire il precedente
        Label textNode = new Label(text);
        textLabel.getChildren().add(textNode);
    }

    @Override
    public void show(GameEvent event) {
        setText(event.getEventMessage());
    }

    @Override
    public void clear() {
        textLabel.getChildren().clear();
        textLabel.getChildren().add(new Label(defaultMessage)); //setto il text a testo vuoto per evitare spostamento dei componenti
    }
}
