package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.model.ApplicationExitModel;
import ch.supsi.connectfour.frontend.view.ApplicationExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ApplicationExitController {
    private static ApplicationExitController instance;
    private final ApplicationExitModel model;
    private final ApplicationExitView view;
    private Stage primaryStage;

    public static ApplicationExitController getInstance() {
        if (instance == null) {
            instance = new ApplicationExitController();
        }
        return instance;
    }

    private ApplicationExitController() {
        view = new ApplicationExitView();
        model = new ApplicationExitModel();
    }

    public ApplicationExitController build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
        return this;
    }

    public void manageExit() {
        if (view.showConfirmationDialog(model.translate("label.close_confirmation"), model.translate("label.confirmation"), model.translate("label.confirm"), model.translate("label.cancel"), null)) {
            this.primaryStage.close();
        }
    }
}
