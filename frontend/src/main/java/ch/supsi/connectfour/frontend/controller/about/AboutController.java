package ch.supsi.connectfour.frontend.controller.about;

import ch.supsi.connectfour.frontend.model.about.AboutModel;
import ch.supsi.connectfour.frontend.model.about.IAboutModel;
import ch.supsi.connectfour.frontend.view.about.AboutView;
import ch.supsi.connectfour.frontend.view.about.IAboutView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class AboutController implements IAboutController {

    /* self reference */
    private static AboutController instance;

    /* stage */
    private final Stage stage;

    /* view */
    private IAboutView aboutView;


    /**
     * Create a new scene, load the about fxml file and save the controller into aboutView field.
     * Set the fxml content as the stage scene
     */
    private AboutController() {
        stage = new Stage();
        try {
            URL fxmlUrl = getClass().getResource("/about.fxml");
            if (fxmlUrl == null) {
                return;
            }

            IAboutModel aboutModel = new AboutModel();
            aboutModel.translateAndSave();

            FXMLLoader aboutLoader = new FXMLLoader(fxmlUrl);
            aboutLoader.setControllerFactory(param -> new AboutView(aboutModel));
            BorderPane content = aboutLoader.load();
            aboutView = aboutLoader.getController();

            Scene scene = new Scene(content);
            stage.setScene(scene);

        } catch (IOException e) {
            System.err.printf("Unable to load about view: %s\n", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @return the instance of this class
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
    public void manageAbout() {
        stage.setResizable(false);
        aboutView.setOnActButton(e -> stage.close());
        stage.show();
    }
}