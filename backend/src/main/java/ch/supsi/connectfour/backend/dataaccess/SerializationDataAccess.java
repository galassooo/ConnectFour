package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.business.serialization.SerializationDataAccessInterface;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.TestOnly;


import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class SerializationDataAccess implements SerializationDataAccessInterface {
    private static SerializationDataAccess instance;

    private SerializationDataAccess() {
        instance = this;
    }

    public static SerializationDataAccess getInstance() {
        if (instance == null)
            return new SerializationDataAccess();
        return instance;
    }
    @Override
    public boolean persist(final ConnectFourBusinessInterface model, final File directory) { // TODO: it's not a directory but a file
        try {
            if (!directory.createNewFile()) {
                System.out.println("ERRORE nella creazione del file di salvataggio");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(directory, model);
        } catch (IOException e ) {
            System.out.println("ERRORE nella scrittura su file del salvataggio");
            // TODO: handle exception -- this is generic and is catching everything
        }

        return true;
    }

    @Override
    public ConnectFourModel getSave(final File file) {
        if (file == null || !file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(file, ConnectFourModel.class);
        } catch (StreamReadException | DatabindException e) {
            e.printStackTrace();
            // TODO: handle
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: handle
        }
        return null;
    }
    @TestOnly
    public static void main(String[] args) {
        PlayerModel p1 = new PlayerModel("A", 1);
        PlayerModel p2 = new PlayerModel("B", 2);
        ConnectFourModel connectFourModel = new ConnectFourModel(p1, p2);
        SerializationDataAccess serializationDataAccess = new SerializationDataAccess();

        if (serializationDataAccess.persist(connectFourModel, new File("C:\\Users\\alexr\\Desktop\\test\\yolo.json"))) {
            System.out.println("SUCCESS!");
        } else {
            System.out.println(":(");
        }
        ConnectFourModel connectFourModel1 = serializationDataAccess.getSave( new File("C:\\Users\\alexr\\Desktop\\test\\yolo.json"));
        System.out.println(connectFourModel1);
    }


}
