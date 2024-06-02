package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.controller.about.AboutController;
import ch.supsi.connectfour.frontend.controller.about.IAboutController;
import ch.supsi.connectfour.frontend.controller.exit.ApplicationExitController;
import ch.supsi.connectfour.frontend.controller.exit.IExitController;
import ch.supsi.connectfour.frontend.controller.game.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.controller.game.IGameController;
import ch.supsi.connectfour.frontend.controller.help.HelpController;
import ch.supsi.connectfour.frontend.controller.help.IHelpController;
import ch.supsi.connectfour.frontend.controller.preferences.IPreferencesController;
import ch.supsi.connectfour.frontend.controller.preferences.PreferencesFrontendController;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuBarDispatcher {
    /* controllers */
    private static final IGameController connectFourFrontendController;
    private static final IAboutController aboutController;
    private static final IPreferencesController preferencesFrontendController;
    private static final IHelpController helpController;
    private static final IExitController exitController;

    /* static initializer for static fields */
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
        aboutController.manageAbout();
    }

    /**
     * handle 'help' request by delegating it to a suitable controller
     */
    public void showHelp() {
        helpController.manageHelp();
    }
}