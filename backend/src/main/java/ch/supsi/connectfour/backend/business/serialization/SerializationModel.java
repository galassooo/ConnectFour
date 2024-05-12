package ch.supsi.connectfour.backend.business.serialization;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.application.serialization.SerializationBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.dataaccess.SerializationDataAccess;

import java.io.File;
import java.lang.annotation.*;

public class SerializationModel implements SerializationBusinessInterface {
    private static SerializationModel instance;
    private final SerializationDataAccessInterface dataAccess;

    private SerializationModel() {
        instance = this;
        this.dataAccess = SerializationDataAccess.getInstance();
    }
    public static SerializationModel getInstance() {
        if (instance == null) {
            return new SerializationModel();
        }
        return instance;
    }

    @Override
    public ConnectFourModel getSave(final File file) {
        return dataAccess.getSave(file);
    }

    @Override
    public boolean persist(final ConnectFourBusinessInterface model, final File directory) {
        return dataAccess.persist(model, directory);
    }
}
