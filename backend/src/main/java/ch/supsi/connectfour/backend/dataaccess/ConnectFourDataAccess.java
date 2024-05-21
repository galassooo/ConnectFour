package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ConnectFourDataAccess implements ConnectFourDataAccessInterface {
    protected static ConnectFourDataAccess instance;

    public static ConnectFourDataAccess getInstance() {
        if (instance == null) {
            instance = new ConnectFourDataAccess();
        }
        return instance;
    }

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
            loadedGame = mapper.readValue(file, ConnectFourModel.class);
        } catch (final IOException e) {
            System.err.println("Error reading file: " + file.getAbsolutePath());
            return null;
        }
        return loadedGame;
    }

    @Override
    public boolean persist(@NotNull final ConnectFourBusinessInterface game, @NotNull final File outputFile) {
        try {
            // If the file exists already then it means it was created already, so no need to try to create it again. If the creation fails then return an error
            if (!outputFile.exists() && !outputFile.createNewFile()) {
                System.err.println("ERRORE nella creazione del file di salvataggio");
                return false;
            }
        } catch (final IOException e) {
            // TODO: do something clever with this exception
        }
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(outputFile, game);
        } catch (IOException e) {
            System.err.println("ERRORE nella scrittura su file del salvataggio");
            return false;
            // TODO: do something clever with this exception
        }
        return true;
    }
}
