package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourBusiness;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ConnectFourDataAccess implements ConnectFourDataAccessInterface {

    /* self reference */
    protected static ConnectFourDataAccess instance;

    /* constructor */
    protected ConnectFourDataAccess() {
    }

    public static ConnectFourDataAccess getInstance() {
        if (instance == null) {
            instance = new ConnectFourDataAccess();
        }
        return instance;
    }

    //ALEX
    @Override
    public ConnectFourBusinessInterface getSave(@NotNull final File file) {
        // Check if the file is valid: exists, is a file and can be read
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        // The mapper will handle the deserialization of the game save, whereas the loadedGame will hold the game loaded by the mapper
        final ObjectMapper mapper = new ObjectMapper();
        final ConnectFourBusinessInterface loadedGame;

        try {
            loadedGame = mapper.readValue(file, ConnectFourBusiness.class);
        } catch (StreamReadException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return loadedGame;
    }

    //ALEX
    @Override
    public boolean persist(@NotNull final ConnectFourBusinessInterface game, @NotNull final File outputFile) {
        try {
            // If the file exists already then it means it was created already, so no need to try to create it again. If the creation fails then return an error
            if (!outputFile.exists() && !outputFile.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(outputFile, game);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
