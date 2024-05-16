package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourDataAccessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
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
    public ConnectFourModel getSave(@NotNull final File file) {
        // Check if the file is valid: exists, is a file and can be read
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(file, ConnectFourModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file: " + file.getAbsolutePath());;
            return null;
        }
    }

    @Override
    public boolean persist(@NotNull final ConnectFourBusinessInterface game, @NotNull final File outputFile) {
        try {
            // If the file exists already then it means it was created already, so no need to try to create it again. If the creation fails then return an error
            if (!outputFile.exists() && !outputFile.createNewFile()) {
                System.out.println("ERRORE nella creazione del file di salvataggio");
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(outputFile, game);
        } catch (IOException e ) {
            System.out.println("ERRORE nella scrittura su file del salvataggio");
            return false;
            // TODO: handle exception -- this is generic and is catching everything
        }
        return true;
    }
}
