package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.HelpView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class HelpController {

    private HelpView helpView;

    private static HelpController instance;

    private final Stage stage = new Stage();

    private final TranslationsController translations = TranslationsController.getInstance();

    /**
     *
     * @return the istance of this class
     */
    public static HelpController getInstance() {
        if (instance == null) {
            instance = new HelpController();
        }
        return instance;
    }
    private HelpController(){
        try {
            URL fxmlUrl = getClass().getResource("/help.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader aboutLoader = new FXMLLoader(fxmlUrl);
            BorderPane content = aboutLoader.load();
            helpView = aboutLoader.getController();

            helpView.setCloseButtonAction((e) -> stage.close());

            Scene scene = new Scene(content);
            stage.setScene(scene);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the popup
     */
    public void showHelpPopUp(){
        helpView.loadImage("/images/board.png");
        helpView.setCloseButtonText(translations.translate("label.close"));
        helpView.setHowToPlayLabel(translations.translate("label.help_title"));
        helpView.setHelpLabel(translations.translate("label.help_start"));
        helpView.setCloseButtonAction((e) -> stage.close());
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showSecondScreen());
        stage.show();
    }

    private void showSecondScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel(translations.translate("label.help_load"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showThirdScreen());
    }

    private void showThirdScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel(translations.translate("label.help_save"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showFourthScreen());
    }

    private void showFourthScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel(translations.translate("label.help_preferences"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showFifthScreen());
    }
    private void showFifthScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel(translations.translate("label.help_finished"));
        helpView.setNextButtonLabel(translations.translate("label.play"));
        helpView.setNextButtonAction((e) -> stage.close());
    }
}
