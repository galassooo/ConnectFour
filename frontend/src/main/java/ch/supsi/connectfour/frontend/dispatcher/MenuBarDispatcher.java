package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

//OK
public class MenuBarDispatcher {
    /* controllers */
    private static final ConnectFourFrontendController connectFourFrontendController;
    private static final AboutController aboutController;
    private static final PreferencesFrontendController preferencesFrontendController;
    private static final HelpController helpController;
    private static final ApplicationExitController exitController;

    //initialize fields
    static {
        connectFourFrontendController = ConnectFourFrontendController.getInstance();
        aboutController = AboutController.getInstance();
        preferencesFrontendController = PreferencesFrontendController.getInstance();
        helpController = HelpController.getInstance();
        exitController = ApplicationExitController.getInstance();
    }

    /* components */
    @FXML
    public MenuItem saveMenuItem;

    /**
     * handle 'new game' request by delegating it to a suitable controller
     */
    public void newGame() {
        connectFourFrontendController.manageNew();
    }

    /**
     * handle 'open game' request by delegating it to a suitable controller
     */
    public void openGame() {
        connectFourFrontendController.manageOpen();
    }

    /**
     * handle 'save game' request by delegating it to a suitable controller
     */
    public void saveGame() {
        connectFourFrontendController.manageSave();
    }
    /**
     * handle 'save as' request by delegating it to a suitable controller
     */
    public void saveGameAs() {
        connectFourFrontendController.manageSaveAs();
    }

    /**
     * handle 'quit' request by delegating it to a suitable controller
     */
    public void quit() {
        exitController.manageExit();
    }

    /**
     * handle 'preferences' request by delegating it to a suitable controller
     */
    public void preferences() {
        preferencesFrontendController.managePreferences();
    }
    /**
     * handle 'about' request by delegating it to a suitable controller
     */
    public void showAbout() {
        aboutController.showAboutPopUp();
    }
    /**
     * handle 'help' request by delegating it to a suitable controller
     */
    public void showHelp() {
        helpController.showHelpPopUp();
    }

}