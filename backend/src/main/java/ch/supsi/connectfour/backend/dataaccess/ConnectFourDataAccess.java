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

    /* field */
    private static final String EXTENSION = ".json";

    /* constructor */
    protected ConnectFourDataAccess() {
    }

    public static ConnectFourDataAccess getInstance() {
        if (instance == null) {
            instance = new ConnectFourDataAccess();
        }
        return instance;
    }

    /**
     * Tries to retrieve the game save associated with a file
     *
     * @param file the file representing the game save
     * @return the loaded game if the retrieval completed successfully, null otherwise
     */
    @Override
    public ConnectFourBusiness getSave(@NotNull final File file) {
        // Check if the file is valid: exists, is a file and can be read
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        // The mapper will handle the deserialization of the game save, whereas the loadedGame will hold the game loaded by the mapper
        final ObjectMapper mapper = new ObjectMapper();
        final ConnectFourBusiness loadedGame;

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

    @Override
    public String getFileExtension() {
        return EXTENSION;
    }

    /**
     * Tries to persist a game object to a file in the filesystem
     *
     * @param game       an object representing the game to persist
     * @param outputFile the file to serialize the game to
     * @return wether or not the persistence operation completed successfully
     */
    @Override
    public boolean persist(@NotNull final ConnectFourBusinessInterface game, @NotNull final File outputFile) {
        try {
            // If the file exists already then it means it was created already, so no need to try to create it again. If the creation fails then return an error
            if (!outputFile.exists() && !outputFile.createNewFile()) {
                return false;
            }
        } catch (SecurityException e) {
            System.err.printf("An error occurred while trying to check if file %s exists%n", outputFile);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.printf("An error occurred while trying to create file %s%n", outputFile);
            e.printStackTrace();
        }
        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(outputFile, game);
        } catch (IOException e) {
            System.err.printf("An error occurred while trying to write to file %s with an ObjectMapper%n", outputFile);
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
