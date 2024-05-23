package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class ColumnsSelectorDispatcher {
    @FXML
    private GridPane pane;

    private final GameController gameController = GameController.getInstance();
    @FXML
    void initialize(){
        disableButtons(true);
    }

    /**
     * Set all buttons on disabled/enabled
     * @param disable true for disabling buttons or false to enable them
     */
    public void disableButtons(boolean disable){
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Button btn) {
                        btn.setDisable(disable);
                    }
                }
            }
        }
    }

    public void playerMove(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Button button){
            gameController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }

}
