package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ColumnsSelectorDispatcher {

    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    public void playerMove(ActionEvent actionEvent) {
        if(actionEvent.getSource() instanceof Button button){
            connectFourFrontendController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }

}
