package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;
import ch.supsi.connectfour.frontend.view.HelpView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelpController {

    private HelpView helpView;

    private static HelpController instance;

    private final Stage stage = new Stage();

    private final TranslationsController translations = TranslationsController.getInstance();

    /**
     * @return the istance of this class
     */
    public static HelpController getInstance() {
        if (instance == null) {
            instance = new HelpController();
        }
        return instance;
    }

    private HelpController() {
        try {
            URL fxmlUrl = getClass().getResource("/help.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader aboutLoader = new FXMLLoader(fxmlUrl);
            BorderPane content = aboutLoader.load();
            helpView = aboutLoader.getController();


            Scene scene = new Scene(content);
            stage.setScene(scene);

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/help/questionMark.png"))));
            stage.setTitle(translations.translate("label.help"));
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the popup
     */
    public void showHelpPopUp() {
        helpView.loadImage("/images/help/new.jpg");

        helpView.setHowToPlayLabel(translations.translate("label.help_title"));
        helpView.setHelpLabel(translations.translate("label.help_start"));
        helpView.removePreviousButton();
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showSecondScreen());
        stage.show();
    }

    private void showSecondScreen() {
        helpView.addPreviousButton();
        helpView.setPreviousButtonAction(e -> showHelpPopUp());
        helpView.setPreviousButtonText(translations.translate("label.previous"));
        helpView.loadImage("/images/help/open.jpg");
        helpView.setHelpLabel(translations.translate("label.help_load"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showThirdScreen());
    }

    private void showThirdScreen() {
        helpView.loadImage("/images/help/columns.jpg");
        helpView.setPreviousButtonAction(e -> showSecondScreen());
        helpView.setHelpLabel(translations.translate("label.help_column"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showFourthScreen());
    }

    private void showFourthScreen() {
        helpView.loadImage("/images/help/save.jpg");
        helpView.setPreviousButtonAction(e -> showThirdScreen());
        helpView.setHelpLabel(translations.translate("label.help_save"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showFifthScreen());
    }

    private void showFifthScreen() {
        helpView.loadImage("/images/help/preferences.jpg");
        helpView.setPreviousButtonAction(e -> showFourthScreen());
        helpView.setHelpLabel(translations.translate("label.help_preferences"));
        helpView.setNextButtonLabel(translations.translate("label.next"));
        helpView.setNextButtonAction((e) -> showSixthScreen());
    }
    private void showSixthScreen() {
        helpView.loadImage("/images/help/play.jpg");
        helpView.setPreviousButtonAction(e -> showFifthScreen());
        helpView.setHelpLabel(translations.translate("label.help_finished"));
        helpView.setNextButtonLabel(translations.translate("label.play"));
        helpView.setNextButtonAction((e) -> stage.close());
    }
}
