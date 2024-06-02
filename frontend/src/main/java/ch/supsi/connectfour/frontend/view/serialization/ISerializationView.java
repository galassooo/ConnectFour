package ch.supsi.connectfour.frontend.view.serialization;

import java.io.File;

/**
 * Defines the behaviour that a generic serialization view should expose to the controller
 */
public interface ISerializationView {
    File askForDirectory(File initialDirectory);

    File askForFile();

    void showMessage(boolean wasSuccessful);

    boolean showConfirmationDialog();

    String showInputDialog();
}