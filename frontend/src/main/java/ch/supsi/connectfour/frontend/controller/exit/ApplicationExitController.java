package ch.supsi.connectfour.frontend.controller.exit;

import ch.supsi.connectfour.frontend.model.exit.ApplicationExitModel;
import ch.supsi.connectfour.frontend.view.exit.ApplicationExitView;
import ch.supsi.connectfour.frontend.view.exit.IApplicationExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class ApplicationExitController implements IExitController {

    /* self reference */
    private static ApplicationExitController instance;
    /* view */
    private final IApplicationExitView view;
    /* stage */
    private Stage primaryStage;

    private ApplicationExitController() {
        ApplicationExitModel model = ApplicationExitModel.getInstance();
        view = new ApplicationExitView(model);
    }

    /**
     * @return an instance of this class
     */
    public static ApplicationExitController getInstance() {
        if (instance == null) {
            instance = new ApplicationExitController();
        }
        return instance;
    }

    /**
     * Initialize instance's field
     *
     * @param primaryStage root stage
     */
    public void build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Handle exit request
     */
    public void manageExit() {
        if (view.showConfirmationDialog()) {
            this.primaryStage.close();
        }
    }
}