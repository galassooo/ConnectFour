package ch.supsi.connectfour.backend.application.connectfour;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private static ConnectFourBusinessInterface model;

    // implement singleton pattern
    // implement methods declared in ConnectFourBusinessInterface
    /*
    Controller uses an instance of model -> model uses an instance of data access
    Controller uses the model to coordinate the execution of the requests, for example:

    boolean movePlayer(Player player, int col) {
        if (model.canInsert(player)) {
            model.insert(player, col);
        }
        if (model.isGameFinished()) {
            model.setFinished();
            // handle what happens when the game is finished
        }
    }
     */
}
