package ch.supsi.connectfour.frontend.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.function.Consumer;

public class AboutView {
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
    void initialize() {
        URL imageUrl = getClass().getResource("/images/about/board.png");
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        imageView.setImage(image);
    }

    public void setAboutConnectFourLabel(String content) {
        aboutConnectFourLabel.setText(content);
    }

    public void setBuiltOnLabel(String content) {
        builtOnLabel.setText(content);
    }

    public void setRuntimeVersionLabel(String content) {
        runtimeVersionLabel.setText(content);
    }

    public void setPoweredByLabel(String content) {
        poweredByLabel.setText(content);
    }

    public void setButtonText(String content) {
        button.setText(content);
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
