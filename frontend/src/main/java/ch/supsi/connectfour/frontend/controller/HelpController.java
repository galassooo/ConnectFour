package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.model.HelpModel;
import ch.supsi.connectfour.frontend.view.IHelpView;
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

public class HelpController {

    private static final int NUM_SCREENS = 6;
    private static final String imagesResourceFormat = "/images/help/%d.jpg";
    private static final String labelsFormat = "label.help_%d";
    private static HelpController instance;
    private final Stage stage;
    private final List<HelpModel> models = new ArrayList<>();
    private IHelpView helpView;

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
     * @return the istance of this class
     */
    public static HelpController getInstance() {
        if (instance == null) {
            instance = new HelpController();
        }
        return instance;
    }

    void buildScreens() {
        for (int i = 1; i <= NUM_SCREENS; i++) {
            int finalI = i;
            models.add(new HelpModel(
                    String.format(imagesResourceFormat, i),
                    "label.help_title",
                    String.format(labelsFormat, i),
                    i == NUM_SCREENS ? "label.play" : "label.next",
                    "label.previous",
                    (e) -> show(finalI - 2),
                    i != NUM_SCREENS ? (e) -> show(finalI) : (e) -> stage.close(),
                    i != 1
            ));
        }
    }

    void show(int index) {
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
