package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.business.symbols.Symbol;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class BoardView implements Viewable {
    @FXML
    private GridPane gridPaneSymbols;
    @FXML
    private GridPane gridPaneColor;

    @FXML
    public void initialize(){
        gridPaneSymbols.setStyle("-fx-grid-lines-visible: true;");

        URL imageUrl = getClass().getResource("/images/board.png");
        if(imageUrl == null){
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        boardLayer.setImage(image);
    }
    public void setCellSymbol(int row, int column, Symbol symbol) {
        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        ((ImageView) child).setImage(new Image(symbol.getResource().toExternalForm()));
                        return;
                    }
                }
            }
        }
    }
    public void setCellBackground(int row, int column, String colorCode){
        Color color = Color.web(colorCode);
        for (javafx.scene.Node node : gridPaneColor.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Pane pane) {
                        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                        return;
                    }
                }
            }
        }
    }

    public void clearGrid() {
        URL imageUrl = getClass().getResource("/images/pawns/plain.png");
        if(imageUrl == null){
            System.err.println("Error while loading transparent png image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());

        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setText("");
                    }
                }
            }
        }
        Color whiteColor = Color.WHITE;
        for (javafx.scene.Node node : gridPaneColor.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Pane pane) {
                        pane.setBackground(new Background(new BackgroundFill(whiteColor, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            }
        }
    }
}
