package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.game.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.controller.game.IGameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColumnsSelectorDispatcher {

    /* controller */
    private static final IGameController connectFourFrontendController;

    static {
        connectFourFrontendController = ConnectFourFrontendController.getInstance();
    }

    /* components */
    @FXML
    private GridPane pane;

    @FXML
    void initialize() {
    }

    /**
     * used to retrieve buttons
     *
     * @return a list containing the REFERENCE to the columns buttons
     */
    public List<Button> getButtons() {
        List<Button> buttonList = new ArrayList<>();
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Button btn) {
                        buttonList.add(btn);
                    }
                }
            }
        }
        return buttonList;
    }

    /**
     * handle an event generated by a JavaFX component such as buttons
     *
     * @param actionEvent event generated by a javaFX component
     */
    public void playerMove(@NotNull ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof Button button) {
            connectFourFrontendController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }
}