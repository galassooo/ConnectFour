package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.frontend.view.PreferencesView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PreferencesController {

    private PreferencesView preferencesView;
    private static PreferencesController instance;
    private final ch.supsi.connectfour.backend.application.preferences.PreferencesController backendController = ch.supsi.connectfour.backend.application.preferences.PreferencesController.getInstance();
    private final Stage stage = new Stage();

    public static PreferencesController getInstance() {
        if (instance == null) {
            instance = new PreferencesController();
        }
        return instance;
    }

    private PreferencesController() {
        try {
            URL fxmlUrl = getClass().getResource("/preferences.fxml");
            if (fxmlUrl == null) {
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load());
            preferencesView = loader.getController();

            preferencesView.setOnSaveButton((e) -> {
                // Handle saving preferences
                String language = preferencesView.getSelectedLanguage();
                backendController.setPreference("language-tag", language);

                String playerOneColor = preferencesView.getPlayerOneColor();
                backendController.setPreference("player-one-color", playerOneColor);

                String playerTwoColor = preferencesView.getPlayerTwoColor();
                backendController.setPreference("player-two-color", playerTwoColor);

                String playerOneShape = preferencesView.getPlayerOneShape();
                backendController.setPreference("player-one-shape", playerOneShape);

                String playerTwoShape = preferencesView.getPlayerTwoShape();
                backendController.setPreference("player-two-shape", playerTwoShape);

                System.out.println("Preferences Saved:");
                System.out.println("Language: " + language);
                System.out.println("Player 1 Color: " + playerOneColor);
                System.out.println("Player 1 Shape: " + playerOneShape);
                System.out.println("Player 2 Color: " + playerTwoColor);
                System.out.println("Player 2 Shape: " + playerTwoShape);
                stage.close();
            });

            preferencesView.setOnCancelButton((e) -> stage.close());

            stage.setScene(scene);
            stage.setTitle("Preferences");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void managePreferences(){
        stage.setResizable(false);
        stage.show();
    }
}
