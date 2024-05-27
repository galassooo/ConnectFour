package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColumnsSelectorDispatcher {
    @FXML
    private GridPane pane;

    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    @FXML
    void initialize() {
    }
    public List<Button> getButtons(){
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
