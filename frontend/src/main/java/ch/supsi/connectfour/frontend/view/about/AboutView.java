package ch.supsi.connectfour.frontend.view.about;

import ch.supsi.connectfour.frontend.model.about.AboutModel;
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

//OK
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

    /**
     * Initializes the view by loading the necessary components through queries to the model class
     * and loads the board image
     */
    @FXML
    void initialize() {

        IAboutModel model = new AboutModel(); //model class

        URL imageUrl = getClass().getResource("/images/about/board.png");
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        imageView.setImage(image);

        //query data
        poweredByLabel.setText(model.getPoweredByLabel());
        button.setText(model.getCloseText());
        builtOnLabel.setText(model.getBuiltOnLabel());
        runtimeVersionLabel.setText(model.getRuntimeVersionLabel());
        dateValue.setText(model.getDate());
        versionValue.setText(model.getVersion());
        developersValue.setText(model.getDevelopers());
        this.aboutConnectFourLabel.setText(model.getAboutConnectFourLabel());

    }

    /**
     * set the action of the 'close button'
     *
     * @param eventConsumer the action to be performed on press
     */
    public void setOnActButton(@NotNull Consumer<ActionEvent> eventConsumer) {
        button.setOnAction(eventConsumer::accept);
    }
}
