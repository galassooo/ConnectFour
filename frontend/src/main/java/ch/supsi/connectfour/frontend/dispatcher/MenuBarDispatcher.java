package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.controller.AboutController;
import ch.supsi.connectfour.frontend.controller.GameController;
import ch.supsi.connectfour.frontend.controller.HelpController;
import javafx.event.ActionEvent;

public class MenuBarDispatcher {
    private final GameController gameController = GameController.getInstance();

    private final AboutController aboutController = AboutController.getInstance();

    private final HelpController helpController = HelpController.getInstance();
    public void newGame(ActionEvent actionEvent) {
        gameController.newGame();
    }

    public void openGame(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }

    public void saveGame(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }

    public void saveGameAs(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
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