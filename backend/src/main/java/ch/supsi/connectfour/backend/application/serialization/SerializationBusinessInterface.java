package ch.supsi.connectfour.backend.application.serialization;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;

import java.io.File;

public interface SerializationBusinessInterface {
    ConnectFourModel getSave(final File file);
    boolean persist(final ConnectFourBusinessInterface model, final File directory);
}
