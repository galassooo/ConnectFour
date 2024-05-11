package ch.supsi.connectfour.frontend.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class BoardView {
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize(){
        gridPane.setStyle("-fx-grid-lines-visible: true;");
        clearGrid(); //valutare di cambiare direttamente il file fxml
    }
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
    private void clearGrid(){
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
