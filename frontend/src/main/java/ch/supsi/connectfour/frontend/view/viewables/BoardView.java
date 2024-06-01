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

    /* Field - URL symbol provider */
    private final SymbolProviderApplication<URL> symbolProvider = new SymbolProvider<>();

    /* Components */
    @FXML
    private GridPane gridPaneSymbols;
    @FXML
    private GridPane gridPaneColor;
    @FXML
    private ImageView boardLayer;

    /**
     * Initializes the board view, setting grid lines visible and loading the board image.
     */
    @FXML
    public void initialize() {
        // Set grid lines visible for symbol grid pane
        gridPaneSymbols.setStyle("-fx-grid-lines-visible: true;");

        // Load board image
        URL imageUrl = getClass().getResource("/images/board.png");
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        boardLayer.setImage(image);
    }

    /**
     * Sets the symbol for a cell at the specified row and column.
     *
     * @param row    The row index of the cell.
     * @param column The column index of the cell.
     * @param symbol The symbol to set.
     */
    private void setCellSymbol(int row, int column, SymbolInterface symbol) {
        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        // Set cell symbol image
                        ((ImageView) child).setImage(new Image(symbolProvider.translate(SymbolInterface::getAsResource, symbol).toExternalForm()));
                        return;
                    }
                }
            }
        }
    }

    /**
     * Sets the background color for a cell at the specified row and column.
     *
     * @param row       The row index of the cell.
     * @param column    The column index of the cell.
     * @param colorCode The color code to set.
     */
    private void setCellBackground(int row, int column, String colorCode) {
        Color color = Color.web(colorCode);
        for (javafx.scene.Node node : gridPaneColor.getChildren()) {
            if (node instanceof AnchorPane anchorPane && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Pane pane) {
                        // Set cell background color
                        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                        return;
                    }
                }
            }
        }
    }

    /**
     * Shows the game event on the board.
     *
     * @param event The game event to show.
     */
    @Override
    public void show(GameEvent event) {
        if (event instanceof ValidMoveEvent e) {
            // Update cell symbol and background for a valid move event
            setCellSymbol(e.getRow(), e.getColumn(), e.getPlayerSymbol());
            setCellBackground(e.getRow(), e.getColumn(), e.getPlayerColor());
        }
    }

    /**
     * Clears the board, resetting all cells to their default state.
     */
    @Override
    public void clear() {
        // Load transparent pawn image
        URL imageUrl = getClass().getResource("/images/pawns/plain.png");
        if (imageUrl == null) {
            System.err.println("Error while loading transparent png image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());

        // Reset cell symbols to transparent pawns
        for (javafx.scene.Node node : gridPaneSymbols.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof ImageView) {
                        ((ImageView) child).setImage(image);
                    }
                }
            }
        }

        // Reset cell backgrounds to white color
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

