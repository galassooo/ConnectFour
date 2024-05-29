package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.ExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

// TODO: per ora non interagisce in nessun modo con il model... valutare
public class ApplicationExitController {
    private static ApplicationExitController instance;
    private static TranslationsController translations;
    private final ExitView view;
    private Stage primaryStage;

    public static ApplicationExitController getInstance() {
        if (instance == null) {
            instance = new ApplicationExitController();
        }
        return instance;
    }

    private ApplicationExitController() {
        view = new ExitView();
        translations = TranslationsController.getInstance();
    }

    public ApplicationExitController build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
        return this;
    }

    public void manageExit() {
        if (view.showConfirmationDialog(translations.translate("label.close_confirmation"), translations.translate("label.confirmation"), translations.translate("label.confirm"), translations.translate("label.cancel"), null)) {
            this.primaryStage.close();
        }
    }
}
