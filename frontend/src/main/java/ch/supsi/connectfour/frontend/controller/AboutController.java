package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.model.AboutModel;
import ch.supsi.connectfour.frontend.view.about.IAboutView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AboutController {
    private static AboutController instance;
    private final Stage stage;
    private IAboutView aboutView;

    private AboutModel model;

    /*Anche se la logica per caricare i componenti dell applicazione è tutta in MainFX
        credo sia meglio caricare il contenuto del popup qui perchè:
        1) il popup non è un elemento necessario all'avvio dell'applicazione (e anche solo questo è valido come motivo)
        2) caricare il popup solo quando l'utente clicca effettivamente sul tasto about è più efficiente a livello di performance
        3) concettualmente ci sta che il controller si occupi inizializzare la view,
           è come il controller della partita che crea la partita eccetera
     */
    private AboutController() {
        stage = new Stage();
        try {
            URL fxmlUrl = getClass().getResource("/about.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader aboutLoader = new FXMLLoader(fxmlUrl);
            BorderPane content = aboutLoader.load();
            aboutView = aboutLoader.getController();

            Scene scene = new Scene(content);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the istance of this class
     */
    public static AboutController getInstance() {
        if (instance == null) {
            instance = new AboutController();
        }
        return instance;
    }

    /**
     * Shows the popup
     */
    public void showAboutPopUp() {
        stage.setResizable(false);
        aboutView.setOnActButton(e-> stage.close());
        stage.show();
    }
}
