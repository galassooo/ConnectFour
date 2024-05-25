package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.controller.AboutController;
import ch.supsi.connectfour.frontend.controller.ConnectFourFrontendController;
import ch.supsi.connectfour.frontend.controller.PreferencesFrontendController;
import javafx.event.ActionEvent;

public class MenuBarDispatcher {
    private final ConnectFourFrontendController connectFourFrontendController = ConnectFourFrontendController.getInstance();
    private final AboutController aboutController = AboutController.getInstance();
    private final PreferencesFrontendController preferencesFrontendController = PreferencesFrontendController.getInstance();

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

    public void preferences(){ preferencesFrontendController.managePreferences(); }

    public void showAbout(ActionEvent actionEvent) {
        aboutController.showAboutPopUp();
    }

    public void showHelp(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }
}