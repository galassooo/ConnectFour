package ch.supsi.connectfour.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InfoBarView implements Viewable {

    @FXML
    private TextFlow textLabel;

    @FXML
    private void initialize(){

    }
    public void setText(String text){
        textLabel.getChildren().clear(); // se voglio pulire il precedene
        Text textNode = new Text(text);
        textLabel.getChildren().add(textNode);
    }

    @Override
    public void clear() {
        textLabel.getChildren().clear();
    }
}
