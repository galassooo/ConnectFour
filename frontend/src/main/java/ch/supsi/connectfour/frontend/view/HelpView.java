package ch.supsi.connectfour.frontend.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.function.Consumer;

public class HelpView {

    @FXML
    private Label helpLabel;

    @FXML
    private Label HowToPlayLabel;

    @FXML
    private ImageView image;

    @FXML
    private Button closeButton;

    @FXML
    private Button nextButton;

    public void setHelpLabel(String content) {
        helpLabel.setText(content);
    }

    public void setHowToPlayLabel(String content) {
        HowToPlayLabel.setText(content);
    }

    public void setCloseButtonText(String text) {
        closeButton.setText(text);
    }

    public void setCloseButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        closeButton.setOnAction(eventConsumer::accept);
    }

    public void setNextButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        nextButton.setOnAction(eventConsumer::accept);
    }

    public void setNextButtonLabel(String label) {
        nextButton.setText(label);
    }

    public void loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Error while loading board image");
            return;
        }
        Image image = new Image(imageUrl.toExternalForm());
        this.image.setImage(image);
    }

}
