package ch.supsi.connectfour.frontend.controller;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class StageManager {
    private static StageManager instance;
    public static final String APP_TITLE = "ConnectFour";
    private Stage root;
    public static StageManager getInstance() {
        if(instance == null){
            instance = new StageManager();
        }
        return instance;
    }
    public void initializeStage(@NotNull Stage root) {
        this.root = root;
        root.setResizable(false);
        root.setTitle(APP_TITLE);
        root.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/application/board.png"))));
        root.setOnCloseRequest(
                windowEvent -> {
                    windowEvent.consume();
                    root.close();
                }
        );
    }

    private StageManager() {
    }
    protected StageManager(Stage stage){
        initializeStage(stage); //forza le subclass a fornire uno stage
    }

    public void setRootAsOwner(@NotNull Dialog<?> dialog){
        dialog.initOwner(root);
    }

    public void showScene(Scene scene){
        root.setScene(scene);
        root.show();
    }
    public void requestApplicationShutDown(){
        root.close();
    }
    public void setStageTitle(String title){
        root.setTitle(title);
    }

    protected Stage getStage(){
        return root;
    }

    /*
    L'implementazione seguente è stata 'forzata' dall'uso di javaFX in quanto le classi FileChooser e DirectoryChooser
    estendono direttamente object senza avere nessuna superclass o interfaccia in comune.

    Idealmente avremmo preferito usare il polimorfismo in quanto permette una maggiore flessibilità, nonostante questo
    abbiamo fornito un getter dello stage a protected per far si che volendo si possa estendere il comportamento
    della classe includendo altri metodi simili a quelli sotto.

    L'idea del getter a public è stata scartata in quanto il mainStage è una componente essenziale dell'applicazione
    e renderne pubblico il riferimento potrebbe portare a diverse situazioni non controllate e dunque 'pericolose'
     */
    public File showFileChooserDialog(@NotNull FileChooser fileChooser){
        return fileChooser.showOpenDialog(root);
    }
    public File showDirectoryChooserDialog(@NotNull DirectoryChooser directoryChooser){
        return directoryChooser.showDialog(root);
    }
}
