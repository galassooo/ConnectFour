package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarDispatcher {
    private static final ConnectFourFrontendController connectFourFrontendController;
    private static final AboutController aboutController;
    private static final PreferencesFrontendController preferencesFrontendController;
    private static final HelpController helpController;
    private static final ApplicationExitController exitController;

    static {
        connectFourFrontendController = ConnectFourFrontendController.getInstance();
        aboutController = AboutController.getInstance();
        preferencesFrontendController = PreferencesFrontendController.getInstance();
        helpController = HelpController.getInstance();
        exitController = ApplicationExitController.getInstance();
    }

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

    public void preferences() {
        preferencesFrontendController.managePreferences();
    }

    public void showAbout() {
        aboutController.showAboutPopUp();
    }

    public void showHelp() {
        helpController.showHelpPopUp();
    }

}