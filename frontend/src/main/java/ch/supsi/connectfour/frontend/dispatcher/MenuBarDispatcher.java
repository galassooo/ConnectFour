package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarDispatcher {
    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    private final AboutController aboutController = AboutController.getInstance();
    private final PreferencesFrontendController preferencesFrontendController = PreferencesFrontendController.getInstance();

    private final HelpController helpController = HelpController.getInstance();
    private final ExitController exitController = new ExitController();
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

    public void saveGameAs() {
        connectFourFrontendController.manageSaveAs();
    }

    public void quit() {
        exitController.manageExit();
    }

    public void preferences(){ preferencesFrontendController.managePreferences(); }

    public void showAbout() {
        aboutController.showAboutPopUp();
    }

    public void showHelp() {
        helpController.showHelpPopUp();
    }

}