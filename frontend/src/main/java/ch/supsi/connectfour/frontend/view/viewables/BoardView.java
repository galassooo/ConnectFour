package ch.supsi.connectfour.frontend.view.viewables;

import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.application.event.ValidMoveEvent;
import ch.supsi.connectfour.backend.application.symbol.SymbolProvider;
import ch.supsi.connectfour.backend.application.symbol.SymbolProviderApplication;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;

public class BoardView implements Viewable {
    @FXML
    private GridPane gridPaneSymbols;
    @FXML
    private GridPane gridPaneColor;

    @FXML
    private ImageView boardLayer;

    private final SymbolProviderApplication<URL> symbolProvider = new SymbolProvider<>();
    @FXML
    public void initialize() {
        gridPaneSymbols.setStyle("-fx-grid-lines-visible: true;");

        URL imageUrl = getClass().getResource("/images/board.png");
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        boardLayer.setImage(image);
    }

    private void setCellSymbol(int row, int column, SymbolInterface symbol) {
        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        ((ImageView) child).setImage(new Image(symbolProvider.translate(SymbolInterface::getAsResource, symbol).toExternalForm()));
                        return;
                    }
                }
            }
        }
    }

    private void setCellBackground(int row, int column, String colorCode) {
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

    @Override
    public void show(GameEvent event) {
        if (event instanceof ValidMoveEvent e) {
            setCellSymbol(e.getRow(), e.getColumn(), e.getPlayerSymbol());
            setCellBackground(e.getRow(), e.getColumn(), e.getPlayerColor());

        }
    }

    @Override
    public void clear() {
        URL imageUrl = getClass().getResource("/images/pawns/plain.png");
        if (imageUrl == null) {
            System.err.println("Error while loading transparent png image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());

        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        ((ImageView) child).setImage(image);
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
