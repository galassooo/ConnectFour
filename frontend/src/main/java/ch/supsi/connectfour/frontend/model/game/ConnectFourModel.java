package ch.supsi.connectfour.frontend.model.game;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourApplication;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ConnectFourModel implements IConnectFourModel {

    /* self reference */
    private static ConnectFourModel instance;

    /* backend controller */
    private final ConnectFourApplication backendController;

    /* data */
    private final HashMap<String, String> translatedLabels = new HashMap<>();

    private ConnectFourModel() {
        this.backendController = ConnectFourApplication.getInstance();
    }

    /**
     * @return an instance of this class
     */
    public static ConnectFourModel getInstance() {
        if (instance == null) {
            instance = new ConnectFourModel();
        }
        return instance;
    }

    /**
     * Delegate move request to the backend controller
     *
     * @param column Column in which the insertion is desired
     * @return The event generated by the move
     */
    @Override
    public GameEvent playerMove(int column) {
        return this.backendController.playerMove(column);
    }

    /**
     * @return true if the match is null, false otherwise
     */
    @Override
    public boolean isCurrentMatchNull() {
        return this.backendController.getCurrentMatch() == null;
    }

    /**
     * Delegate 'create new game' request to the backend controller
     */
    @Override
    public void createNewGame() {
        this.backendController.createNewGame();
    }

    /**
     * Delegates the persistence of the current game to the backend controller. This method is intended to be used
     * when the game was already 'saved as' once.
     *
     * @return true if the persistence operation was successful, false otherwise
     */
    @Override
    public boolean persist() {
        return this.backendController.persist();
    }

    /**
     * Delegates the persistence of the current game to the backendcontroller.
     *
     * @param directory the directory where the game should be saved
     * @param filename  the name of the file to save the game to
     * @return true if the persistence operation was successful, false otherwise
     */
    @Override
    public boolean persist(File directory, String filename) {
        return this.backendController.persist(directory, filename);
    }

    /**
     * @return the name of the save associated with the current match
     */
    @Override
    public String getSaveName() {
        return this.backendController.getSaveName();
    }

    /**
     * Tries to load a match from a file
     *
     * @param file File from which the game is intended to be loaded
     * @return The loaded game, null if it was not loaded successfully
     */
    @Override
    public @Nullable ConnectFourBusinessInterface tryLoadingSave(File file) {
        return this.backendController.tryLoadingSave(file);
    }

    /**
     * Returns the player who is not currently playing
     *
     * @param player current player
     * @return other player
     */
    @Override
    public ConnectFourPlayerInterface getOtherPlayer(ConnectFourPlayerInterface player) {
        return this.backendController.getOtherPlayer(player);
    }

    @Override
    public String getTranslation(String key){
        return translatedLabels.get(key);
    }

    @Override
    public void translateAndSave() {
        List<String> list = List.of("label.insert_name", "label.insert_name_title",
                "label.chosen_directory", "label.correctly_saved","label.not_correctly_saved",
                "label.confirm", "label.error", "label.overwrite_confirmation","label.cancel",
                "label.confirmation","label.success", "label.infobar_welcome"
        );
        TranslationsApplication translator = TranslationsApplication.getInstance();
        list.forEach( key -> translatedLabels.putIfAbsent(key, translator.translate(key)));
    }
}