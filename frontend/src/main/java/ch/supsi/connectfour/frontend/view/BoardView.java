package ch.supsi.connectfour.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;

public class BoardView {
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView boardLayer;

    @FXML
    public void initialize(){
        gridPane.setStyle("-fx-grid-lines-visible: true;");

        URL imageUrl = getClass().getResource("/images/board.png");
        if(imageUrl == null){
            System.err.println("Error while loading image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        boardLayer.setImage(image);
    }
    public void setCellImage(int row, int column, URL urlImage) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        ((ImageView) child).setImage(new Image(urlImage.toExternalForm()));
                        return;
                    }
                }
            }
        }
    }
}
