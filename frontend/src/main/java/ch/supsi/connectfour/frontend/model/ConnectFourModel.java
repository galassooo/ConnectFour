package ch.supsi.connectfour.frontend.model;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;

import java.io.File;

public class ConnectFourModel {
    private static ConnectFourModel instance;
    private final ConnectFourBackendController backendController;

    private ConnectFourModel() {
        this.backendController = ConnectFourBackendController.getInstance();
    }

    public static ConnectFourModel getInstance() {
        if (instance == null) {
            instance = new ConnectFourModel();
        }
        return instance;
    }

    public GameEvent playerMove(int column) {
        return this.backendController.playerMove(column);
    }

    public boolean isCurrentMatchNull() {
        return this.backendController.getCurrentMatch() == null;
    }

    public void createNewGame() {
        this.backendController.createNewGame();
    }

    public boolean persist() {
        return this.backendController.persist();
    }

    public boolean persist(File directory, String filename) {
        return this.backendController.persist(directory, filename);
    }

    public String getSaveName() {
        return this.backendController.getSaveName();
    }

    public ConnectFourBusinessInterface tryLoadingSave(File file) {
        return this.backendController.tryLoadingSave(file);
    }

    public ConnectFourPlayerInterface getOtherPlayer(ConnectFourPlayerInterface player) {
        return this.backendController.getOtherPlayer(player);
    }
}
