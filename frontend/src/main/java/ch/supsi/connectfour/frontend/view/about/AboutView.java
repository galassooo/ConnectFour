package ch.supsi.connectfour.frontend.view.about;

import ch.supsi.connectfour.frontend.model.about.IAboutModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.function.Consumer;

public class AboutView implements IAboutView {

    /* components */
    @FXML
    private Label aboutConnectFourLabel;

    @FXML
    private Label builtOnLabel;

    @FXML
    private Label runtimeVersionLabel;

    @FXML
    private Label poweredByLabel;
    @FXML
    private Button button;

    @FXML
    private ImageView imageView;

    @FXML
    private Label dateValue;

    @FXML
    private Label developersValue;

    @FXML
    private Label versionValue;

    private final IAboutModel model;

    public AboutView(IAboutModel model) {
        this.model = model;
    }

    /**
     * Initializes the view by loading the necessary components through queries to the model class
     * and loads the board image
     */
    @FXML
    void initialize() {

        URL imageUrl = getClass().getResource("/images/about/board.png");
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        imageView.setImage(image);

        //query data
        poweredByLabel.setText(model.getTranslation("label.powered_by"));
        button.setText(model.getTranslation("label.close"));
        builtOnLabel.setText(model.getTranslation("label.built_on"));
        runtimeVersionLabel.setText(model.getTranslation("label.runtime_version"));
        dateValue.setText(model.getDate());
        versionValue.setText(model.getVersion());
        developersValue.setText(model.getDevelopers());
        this.aboutConnectFourLabel.setText(model.getTranslation("label.title"));

    }

    /**
     * Set the action of the 'close button'
     *
     * @param eventConsumer the action to be performed on press
     */
    @Override
    public void setOnActButton(@NotNull Consumer<ActionEvent> eventConsumer) {
        button.setOnAction(eventConsumer::accept);
    }
}
