package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.model.ApplicationExitModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.exit.ApplicationExitView;
import ch.supsi.connectfour.frontend.view.exit.IApplicationExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ApplicationExitController {
    private static ApplicationExitController instance;
    private static TranslationModel translationModel;
    ;
    private final ApplicationExitModel model;
    private final IApplicationExitView view;
    private Stage primaryStage;

    private ApplicationExitController() {
        translationModel = TranslationModel.getInstance();
        model = ApplicationExitModel.getInstance(translationModel.translate("label.close_confirmation"), translationModel.translate("label.confirmation"), translationModel.translate("label.confirm"), translationModel.translate("label.cancel"));
        view = new ApplicationExitView(model);
    }

    public static ApplicationExitController getInstance() {
        if (instance == null) {
            instance = new ApplicationExitController();
        }
        return instance;
    }

    public ApplicationExitController build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    windowEvent.consume();
                    primaryStage.close();
                }
        );
        return this;
    }
    public void setOnCloseRequest() {
        primaryStage.setOnCloseRequest(
                windowEvent -> {
                    windowEvent.consume();
                    primaryStage.close();
                }
        );
    }

    public void manageExit() {
        if (view.showConfirmationDialog()) {
            this.primaryStage.close();
        }
    }
}
