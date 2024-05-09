package ch.supsi.connectfour.backend.dataaccess;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.serialization.SerializationDataAccessInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SerializationDataAccess implements SerializationDataAccessInterface {
    private static SerializationDataAccess instance;
    private static final String SEPARATOR = ":";
    // TODO: where do we save things? -> DirectoryChooser JavaFX

    private SerializationDataAccess() {
        instance = this;
    }
    public static SerializationDataAccess getInstance() {
        if (instance == null) {
            instance = new SerializationDataAccess();
        }
        return instance;
    }
    private boolean isFilePresent(final String inputPath) {
        final File file = new File(inputPath);
        return file.exists();
    }

    @Override
    public ConnectFourModel getSave(final File file) {
        if (file == null) { // TODO: check if it actually makes sense, gotta check if DirectoryChooser only allows valid files to be selected
            // TODO: are there any other checks that must be performed before proceeding?
            return null;
        }
        Class<?> clazz = ConnectFourModel.class;
        Field[] fields = clazz.getDeclaredFields();
        ConnectFourModel model = null;

        try {
            // Handle construction
        } catch (final NoSuchMethodException e) {
            // TODO: handle
            return null;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // TODO: handle
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            // TODO: handle
            return null;
        }

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = reader.readLine().split(SEPARATOR)[1];
                // Set field values
            } catch (IOException e) {
                // TODO: handle
                return null;
            }
        }
        return model;
    }

    /**
     * TODO: DirectoryChooser will allow the user to specify the dir where they want to save the file, then ask for the name they want to give to the file and create an instance of File to propagate down to DataAccess
     *
     */
    @Override
    public boolean persist(final ConnectFourModel model, final File file ) {
        try {
            // A file with the specified path already exists, stop and return
            if (!file.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            // TODO: handle exception
            return false;
        }

        Class<?> clazz = model.getClass();
        Field[] fields = clazz.getDeclaredFields();

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (final IOException e) {
            // TODO: handle exception
            return false;
        }
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                writer.write(String.format("%s%s%s", field.getName(), SEPARATOR,  field.get(model)));
            } catch (final IllegalAccessException e) {
                // TODO: How do we handle exceptions?
                return false;
            } catch (IOException e) {
                // TODO: handle exception
                return false;
            }
        }

        try {
            writer.close();
        } catch (final IOException e) {
            // TODO: handle exception
        }

        return true;
    }
}
