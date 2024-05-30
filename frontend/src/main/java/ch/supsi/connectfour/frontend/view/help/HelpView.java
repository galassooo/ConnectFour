package ch.supsi.connectfour.frontend.view.help;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.function.Consumer;

//OK
public class HelpView implements IHelpView {

    /* components */
    @FXML
    private Label helpLabel;

    @FXML
    private Label HowToPlayLabel;

    @FXML
    private ImageView image;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private HBox hbox;

    /* setters */
    @Override
    public void setHelpLabel(String content) {
        helpLabel.setText(content);
    }

    @Override
    public void setHowToPlayLabel(String content) {
        HowToPlayLabel.setText(content);
    }

    @Override
    public void setPreviousButtonText(String text) {
        previousButton.setText(text);
    }

    @Override
    public void setPreviousButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        previousButton.setOnAction(eventConsumer::accept);
    }

    @Override
    public void setNextButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        nextButton.setOnAction(eventConsumer::accept);
    }
    @Override
    public void setNextButtonLabel(String label) {
        nextButton.setText(label);
    }

    /**
     * remove previous button
     */
    @Override
    public void removePreviousButton() {
        hbox.getChildren().remove(previousButton);
    }

    /**
     * add the previous button
     */
    @Override
    public void addPreviousButton() {
        //rimuovo e aggiungo entrambi per avere ordine corretto
        if (!hbox.getChildren().contains(previousButton)) { //duplicate children check
            hbox.getChildren().remove(nextButton);
            hbox.getChildren().add(previousButton);
            hbox.getChildren().add(nextButton);
        }
    }

    /**
     * attempts to load and display an image with the given path
     * @param path image's path
     */
    @Override
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
