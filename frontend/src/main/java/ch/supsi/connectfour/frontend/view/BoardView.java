package ch.supsi.connectfour.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.net.URL;

public class BoardView implements Viewable{
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView boardLayer;

    /*
    @FXML
    public void initialize(){
        gridPane.setStyle("-fx-grid-lines-visible: true;");
        this.clear();

        URL imageUrl = getClass().getResource("/images/board.png");
        if(imageUrl == null){
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        boardLayer.setImage(image);
    }
    */
    public void setCellText(int row, int column, String text) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setText(text);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void clear() {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setText("");
                    }
                }
            }
        }
    }
}
