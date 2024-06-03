package ch.supsi.connectfour.frontend.controller.exit;

import ch.supsi.connectfour.frontend.model.Translatable;
import ch.supsi.connectfour.frontend.model.exit.ApplicationExitModel;
import ch.supsi.connectfour.frontend.view.exit.ApplicationExitView;
import ch.supsi.connectfour.frontend.view.exit.IApplicationExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
public class ApplicationExitController implements IExitController {

    /* self reference */
    private static ApplicationExitController instance;

    /* stage */
    private Stage primaryStage;

    /* view */
    private final IApplicationExitView view;

    private ApplicationExitController() {
        Translatable model = ApplicationExitModel.getInstance();
        model.translateAndSave();

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
