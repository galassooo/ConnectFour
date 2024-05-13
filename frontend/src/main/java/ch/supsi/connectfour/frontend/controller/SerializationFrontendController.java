package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.serialization.SerializationController;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.frontend.MainFx;
import ch.supsi.connectfour.frontend.view.SerializationView;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;

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
        // TODO: implement, if there's an ongoing game, ask for confirmation, else just open a new game
        if (true) {
            // If the user confirms their choice to open a new game
            if (this.serializationView.showConfirmationDialog("Are you sure you want to start a new game? \n This will overwrite any ongoing games.")) {
                // Handle setting the new game from the backend controller
            }
        }
    }
    // TODO: load messages from translations
    public void manageSave() {
        boolean hasBeenSavedAs = false;
        if (hasBeenSavedAs) {
            this.serializationView.showMessage("Seleziona cartella!", "Seleziona una ca", Alert.AlertType.INFORMATION);
            File dir = this.serializationView.askForDirectory();

            // Check if the dir variable points to something, wether the directory exists on the filesystem and is a directory
            if (dir != null && dir.exists() && dir.isDirectory()) {
                if (this.serializationBackendController.persist(this.connectFourBackendController.getCurrentMatch(), dir)) {
                    this.serializationView.showMessage("Yay salvato correttamente", null ,  Alert.AlertType.INFORMATION);
                } else {
                    this.serializationView.showMessage(":(", null ,  Alert.AlertType.ERROR);
                }
            }
        } else {
            this.manageSaveAs();
        }
    }
    public void manageSaveAs() {
        final File dir = this.serializationView.askForDirectory();
        // TODO: handle with translations
        final String fileName = this.serializationView.showInputDialog("Inserisci il nome del file");

        final File file = new File(dir, fileName);
        boolean wasCreated = false;
        try {
            wasCreated = file.createNewFile();
        } catch (final IOException e) {
            // TODO: handle exceptions
        }
        boolean wasSaved = this.serializationBackendController.persist(this.connectFourBackendController.getCurrentMatch(), file);
        if (!wasCreated || !wasSaved) {
            this.serializationView.showMessage("Something went wrong with the creation of the file, the operation was cancelled.", "Error while creating file", Alert.AlertType.ERROR);
        }

    }
    public void manageOpen() {
        File file = this.serializationView.askForFile();
        // Checking if the file is valid (exists as a File instance, exists in the filesystem and is a file)
        if (file != null && file.exists() && file.isFile()) {
            final ConnectFourModel game = this.serializationBackendController.getSave(file);
            this.connectFourBackendController.setCurrentMatch(game);
        }
    }

}
