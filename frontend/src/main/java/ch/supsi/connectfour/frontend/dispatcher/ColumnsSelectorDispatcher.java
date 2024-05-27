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

public class ColumnsSelectorDispatcher {
    @FXML
    private GridPane pane;

    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    private final TranslationsController translationsController = TranslationsController.getInstance();

    @FXML
    void initialize() {
        disableButtons(true);
        setTooltip(translationsController.translate("label.column_tooltip"));
    }

    /**
     * Set all buttons on disabled/enabled
     *
     * @param disable true for disabling buttons or false to enable them
     */
    public void disableButtons(boolean disable) {
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

    private void setTooltip(String tooltip) {
        for (javafx.scene.Node node : pane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                for (javafx.scene.Node child : anchorPane.getChildren()) {
                    if (child instanceof Button btn) {
                        btn.setTooltip(new Tooltip(tooltip));
                    }
                }
            }
        }
    }

    public void playerMove(ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof Button button) {
            connectFourFrontendController.manageColumnSelection(Integer.parseInt(button.getId()));
        }
    }

}
