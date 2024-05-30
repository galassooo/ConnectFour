package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.model.ApplicationExitModel;
import ch.supsi.connectfour.frontend.model.TranslationModel;
import ch.supsi.connectfour.frontend.view.exit.ApplicationExitView;
import ch.supsi.connectfour.frontend.view.exit.IApplicationExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

//NOT OK
public class ApplicationExitController {

    /* self reference */
    private static ApplicationExitController instance;

    /* stage */
    private Stage primaryStage;

    /* view */
    private final IApplicationExitView view;

    private ApplicationExitController() {
        TranslationModel translationModel = TranslationModel.getInstance();
        //tradurre label direttamente nel model?
        ApplicationExitModel model = ApplicationExitModel.getInstance(translationModel.translate("label.close_confirmation"), translationModel.translate("label.confirmation"), translationModel.translate("label.confirm"), translationModel.translate("label.cancel"));
        view = new ApplicationExitView(model);
    }

    /**
     *
     * @return an instance of this class
     */
    public static ApplicationExitController getInstance() {
        if (instance == null) {
            instance = new ApplicationExitController();
        }
        return instance;
    }

    /**
     * initialize instance's field
     * @param primaryStage root stage
     */
    public void build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * handle exit request
     */
    public void manageExit() {
        if (view.showConfirmationDialog()) {
            this.primaryStage.close();
        }
    }
}
