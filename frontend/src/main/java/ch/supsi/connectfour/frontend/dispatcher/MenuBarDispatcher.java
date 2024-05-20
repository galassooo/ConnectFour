package ch.supsi.connectfour.frontend.dispatcher;

import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.controller.GameController;
import javafx.event.ActionEvent;

public class MenuBarDispatcher {
    private GameController gameController = GameController.getInstance();
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
        // decode this event
        // delegate it to a suitable controller
    }

    public void showHelp(ActionEvent actionEvent) {
        // decode this event
        // delegate it to a suitable controller
    }

}