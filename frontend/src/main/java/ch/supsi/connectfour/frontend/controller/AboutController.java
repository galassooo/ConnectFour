package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.AboutView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AboutController {
    private AboutView aboutView;
    private final Stage stage;

    private static AboutController instance;
    private final static TranslationsController translations;
    static {
        translations = TranslationsController.getInstance();
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
            stage.setTitle(translations.translate("label.about"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the popup
     */
    public void showAboutPopUp() {
        aboutView.setOnActButton((e) -> stage.close());
        aboutView.setPoweredByLabel(translations.translate("label.powered_by"));
        aboutView.setButtonText(translations.translate("label.close"));
        aboutView.setBuiltOnLabel(translations.translate("label.built_on"));
        aboutView.setRuntimeVersionLabel(translations.translate("label.runtime_version"));
        stage.setResizable(false);
        stage.show();
    }
}
