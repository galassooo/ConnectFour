package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.controller.AboutController;
import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.controller.HelpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarDispatcher {
    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    private final AboutController aboutController = AboutController.getInstance();

    private final HelpController helpController = HelpController.getInstance();
    public void newGame(ActionEvent actionEvent) {
        connectFourFrontendController.newGame();

    // TODO: decide how to handle this
    @FXML
    public MenuItem saveMenuItem;


    public void newGame() {
        connectFourFrontendController.manageNew();
    }

    public void openGame() {
        connectFourFrontendController.manageOpen();
    }

    public void saveGame() {
        connectFourFrontendController.manageSave();
    }

    public void saveGameAs(ActionEvent actionEvent) {
        connectFourFrontendController.manageSaveAs();
    }

    public void quit(ActionEvent actionEvent) {
        //todo ha senso creare un controller per sta cosa?
        //secondo me non ha senso usare il gamecontroller, il ciclo di vita dell'applicazione non è relazionato
        // in alcun modo al gioco
        MainFx.getInstance().stop();
    }

    public void showAbout(ActionEvent actionEvent) {
        aboutController.showAboutPopUp();
    }

    public void showHelp(ActionEvent actionEvent) {
        helpController.showHelpPopUp();
    }

}