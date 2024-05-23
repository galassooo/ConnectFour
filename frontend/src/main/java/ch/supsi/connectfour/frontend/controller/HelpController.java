package ch.supsi.connectfour.frontend.controller;

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
        helpView.setHelpLabel("You can start a match by clicking on the button or selecting new match from menu");
        helpView.setCloseButtonAction((e) -> stage.close());
        helpView.setNextButtonLabel("Next");
        helpView.setNextButtonAction((e) -> showSecondScreen());
        stage.show();
    }

    private void showSecondScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel("Or you can load an existing match by clicking on Open...");
        helpView.setNextButtonLabel("Next");
        helpView.setNextButtonAction((e) -> showThirdScreen());
    }

    private void showThirdScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel("To save a game click on Save... or on Save as...");
        helpView.setNextButtonLabel("Next");
        helpView.setNextButtonAction((e) -> showFourthScreen());
    }

    private void showFourthScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel("To edit your preferences you can select preference from the edit menu");
        helpView.setNextButtonLabel("Next");
        helpView.setNextButtonAction((e) -> showFifthScreen());
    }
    private void showFifthScreen(){
        helpView.loadImage("/images/board.png");
        helpView.setHelpLabel("You're ready to play!");
        helpView.setNextButtonLabel("Play!");
        helpView.setNextButtonAction((e) -> stage.close());
    }
}
