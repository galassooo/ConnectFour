package ch.supsi.connectfour.frontend.controller;
import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.business.movedata.MoveData;
import ch.supsi.connectfour.frontend.view.BoardView;

public class GameController {

    private static GameController instance;
    private final ConnectFourBackendController backendController = ConnectFourBackendController.getInstance();

    private BoardView boardView;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    /**
     * Effettua la chiamata al controller in backend per gestire la mossa e gestisce l'esito graficamente
     * @param column colonna nel quale il giocatore intende inserire la pedina
     */
    public void manageColumnSelection(int column){
        MoveData data = backendController.playerMove(column);
        if(data!= null && boardView!= null) { //allora la mossa è andata a buon fine
            boardView.setCellText(data.row(), data.column(), data.player().getName());
        }
    }
}
