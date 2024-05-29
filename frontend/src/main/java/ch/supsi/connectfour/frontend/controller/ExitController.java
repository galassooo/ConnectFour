package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.ExitView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

// TODO: per ora non interagisce in nessun modo con il model... valutare
public class ExitController {
    private static ExitController instance;
    private static TranslationsController translations;
    private ExitView view;
    private Stage primaryStage;

    public static ExitController getInstance() {
        if (instance == null) {
            instance = new ExitController();
        }
        return instance;
    }

    public ExitController() {
        view = new ExitView();
        translations = TranslationsController.getInstance();
    }

    public ExitController build(@NotNull Stage primaryStage) {
        this.primaryStage = primaryStage;
        return this;
    }

    public void manageExit() {
        if (view.showConfirmationDialog(translations.translate("label.close_confirmation"), translations.translate("label.confirmation"), translations.translate("label.confirm"), translations.translate("label.cancel"), null)) {
            this.primaryStage.close();
        }
    }
}
