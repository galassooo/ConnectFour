package ch.supsi.connectfour.backend.application.serialization;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.serialization.SerializationModel;

import java.io.File;

public class SerializationController {
    private static SerializationController instance;
    private final SerializationBusinessInterface model;

    /**
     * Private constructor, initializes both the static reference to this instance of controller
     * and the model
     */
    private SerializationController() {
        instance = this;
        this.model = SerializationModel.getInstance();
    }

    public static SerializationController getInstance() {
        if (instance == null)
            return new SerializationController();
        return instance;
    }

    public ConnectFourModel getSave(final File file) {
        ConnectFourModel model = this.model.getSave(file);
        if (model == null) {
            // TODO: do something
        }
        return model;
    }
    public boolean persist(final ConnectFourBusinessInterface model, final File directory) {
        return this.model.persist(model, directory);
    }
}
