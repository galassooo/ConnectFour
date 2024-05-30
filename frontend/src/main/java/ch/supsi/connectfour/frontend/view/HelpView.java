package ch.supsi.connectfour.frontend.view;

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

public class HelpView implements IHelpView{

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

    public void setHelpLabel(String content) {
        helpLabel.setText(content);
    }

    public void setHowToPlayLabel(String content) {
        HowToPlayLabel.setText(content);
    }

    public void setPreviousButtonText(String text) {
        previousButton.setText(text);
    }

    public void setPreviousButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        previousButton.setOnAction(eventConsumer::accept);
    }

    public void setNextButtonAction(@NotNull Consumer<ActionEvent> eventConsumer) {
        nextButton.setOnAction(eventConsumer::accept);
    }
    public void removePreviousButton(){
        hbox.getChildren().remove(previousButton);
    }

    public void addPreviousButton(){
        //rimuovo e aggiungo entrambi per avere ordine corretto
        if(!hbox.getChildren().contains(previousButton)) { //duplicate children check
            hbox.getChildren().remove(nextButton);
            hbox.getChildren().add(previousButton);
            hbox.getChildren().add(nextButton);
        }
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
