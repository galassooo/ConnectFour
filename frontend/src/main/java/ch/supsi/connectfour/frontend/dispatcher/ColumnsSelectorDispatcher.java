package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColumnsSelectorDispatcher {
    // It's a singleton so might aswell share the reference across the whole class
    private static final ConnectFourFrontendController connectFourFrontendController;

    static {
        connectFourFrontendController = ConnectFourFrontendController.getInstance();
    }

    @FXML
    private GridPane pane;

    @FXML
    void initialize() {
    }

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

    public void playerMove(@NotNull ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof Button button) {
            connectFourFrontendController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }

}
