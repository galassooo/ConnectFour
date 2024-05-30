package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.model.HelpModel;
import ch.supsi.connectfour.frontend.view.help.IHelpView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//OK
public class HelpController {

    /* self reference */
    private static HelpController instance;

    /* screens number */
    private static final int NUM_SCREENS = 6;

    /* formatters */
    private static final String imagesResourceFormat = "%d.jpg";
    private static final String labelsFormat = "label.help_%d";

    /* stage */
    private final Stage stage;

    /* models */
    private final List<HelpModel> models = new ArrayList<>();

    /* view */
    private IHelpView helpView;

    /**
     * load the help fxml file, create a new stage and set the FXML content as scene
     */
    private HelpController() {
        stage = new Stage();
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
            stage.setTitle(HelpModel.getTitle());
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
        buildScreens();
    }

    /**
     * @return the instance of this class
     */
    public static HelpController getInstance() {
        if (instance == null) {
            instance = new HelpController();
        }
        return instance;
    }

    /**
     * create the screens by inserting fields into a model,
     * in this way, we'll have a different model containing different data for each screen
     */
    private void buildScreens() {
        for (int i = 1; i <= NUM_SCREENS; i++) {
            int finalI = i;
            models.add(new HelpModel(

                    /* image resource URL */
                    String.format(imagesResourceFormat, i),

                    /* how to play label */
                    "label.help_title",

                    /* help content translation */
                    String.format(labelsFormat, i),
                    /* if it's constructing the last screen it'll set next button as a 'play button' */
                    i == NUM_SCREENS ? "label.play" : "label.next",

                    /* previous translation */
                    "label.previous",

                    /* shows the previous screen */
                    (e) -> show(finalI - 2),

                    /* if it's constructing the last screen it'll set next button action as a close stage */
                    i != NUM_SCREENS ? (e) -> show(finalI) : (e) -> stage.close(),

                    /* show the previous button for every screen but the first one */
                    i != 1
            ));
        }
    }

    /**
     * update the current screen content
     * @param index page to be shown
     */
    private void show(int index) {
        HelpModel model = models.get(index);
        helpView.loadImage(model.getImagePath());

        helpView.setHowToPlayLabel(model.getHowToPlay());
        helpView.setHelpLabel(model.getHelpText());
        if (!model.isShowPreviousBtn()) {
            helpView.removePreviousButton();
        } else {
            helpView.addPreviousButton();
            helpView.setPreviousButtonText(model.getPreviousBtnText());
            helpView.setPreviousButtonAction(model.getPreviousBtnAction());
        }

        helpView.setNextButtonLabel(model.getNextBtnLabel());
        helpView.setNextButtonAction(model.getNextBtnAction());
        if (index == 0)
            stage.show();
    }

    /**
     * Shows the popup
     */
    public void showHelpPopUp() {
        show(0);
    }
}
