package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.serialization.SerializationController;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.view.SerializationView;
import javafx.scene.control.Alert;

import java.io.File;

public class SerializationFrontendController {
    private static SerializationFrontendController instance;

    private final SerializationController serializationBackendController = SerializationController.getInstance();
    private final ConnectFourBackendController connectFourBackendController = ConnectFourBackendController.getInstance();
    private final SerializationView serializationView;

    private SerializationFrontendController() {
        instance = this;
        serializationView = new SerializationView(MainFx.stage);
    }

    public static SerializationFrontendController getInstance() {
        if (instance == null) {
            return new SerializationFrontendController();
        }
        return instance;
    }

    public void manageNew() {

    }
    // TODO: load messages from translations
    public void manageSave() {
        this.serializationView.showMessage("Seleziona cartella!", "Seleziona una ca", Alert.AlertType.INFORMATION);
        File dir = this.serializationView.askForDirectory();
        // TODO: check if it makes sense to check if the dir is null, can the DirectoryChooser ever return a non-valid directory?
        if (dir != null) {
            if (this.serializationBackendController.persist(this.connectFourBackendController.getCurrentMatch(), dir)) {
                this.serializationView.showMessage("Yay salvato correttamente", null ,  Alert.AlertType.INFORMATION);
            } else {
                this.serializationView.showMessage(":(", null ,  Alert.AlertType.ERROR);
            }
        }
    }
    public void manageSaveAs() {

    }
    public void manageOpen() {
        File file = this.serializationView.askForFile();
        if (file != null) {
            final ConnectFourModel game = this.serializationBackendController.getSave(file);
            this.connectFourBackendController.setCurrentMatch(game);
        }
    }

}
