package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.application.event.GameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;

public class InfoBarView implements Viewable {
    // TODO: llo stato attuale questo viene caricato solo quando viene iniziato un nuovo gioco, eventualmente bisognerebbe chiamare il clear per aggiornarlo quando viene lanciato il gioco o trovare un altro modo
    private static String defaultMessage;
    @FXML
    public Label infoBarText;

    @FXML
    private TextFlow textLabel;

    @FXML
    private void initialize() {
    }

    public static void setDefaultMessage(String message) {
        defaultMessage = message;
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
