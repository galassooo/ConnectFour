package ch.supsi.connectfour.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InfoBarView implements Viewable {
    private static String DEFAULT_MESSAGE = "Welcome to Connect4";

    @FXML
    private TextFlow textLabel;

    @FXML
    private void initialize(){

    }
    public void setText(String text){
        textLabel.getChildren().clear(); // se voglio pulire il precedente
        Text textNode = new Text(text);
        textLabel.getChildren().add(textNode);
    }

    @Override
    public void clear() {
        textLabel.getChildren().clear();
        textLabel.getChildren().add(new Text(DEFAULT_MESSAGE)); //setto il text a testo vuoto per evitare spostamento dei componenti
    }
}
