package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.GameController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ColumnsSelectorDispatcher {

    private final GameController gameController = GameController.getInstance();
    public void playerMove(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Button button){
            gameController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }

}
