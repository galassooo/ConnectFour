package ch.supsi.connectfour.frontend.controller;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBackendController;
import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;
import ch.supsi.connectfour.backend.application.event.*;
import ch.supsi.connectfour.frontend.dispatcher.ColumnsSelectorDispatcher;
import ch.supsi.connectfour.frontend.view.BoardView;
import ch.supsi.connectfour.frontend.view.InfoBarView;


public class GameController implements GameEventHandler {

    private static GameController instance;
    private final ConnectFourBackendController backendController = ConnectFourBackendController.getInstance();

    private ColumnsSelectorDispatcher columnsSelectorDispatcher;

    private BoardView boardView;

    private InfoBarView infoBarView;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {
    }

    public GameController build(BoardView boardView, InfoBarView infoBarView, ColumnsSelectorDispatcher btnDispatcher) {
        if (infoBarView == null || boardView == null) {
            throw new IllegalArgumentException();
        }
        this.columnsSelectorDispatcher = btnDispatcher;
        this.boardView = boardView;
        this.infoBarView = infoBarView;
        return getInstance();
    }

    /**
     * Effettua la chiamata al controller in backend per gestire la mossa e gestisce l'esito graficamente
     *
     * @param column colonna nel quale il giocatore intende inserire la pedina
     */
    public void manageColumnSelection(int column) {
        GameEvent data = backendController.playerMove(column);

        if (data == null) {
            infoBarView.setText("Match is finished! you can't move!");
        } else {
            data.handle(this);
        }
    }

    @Override
    public void handle(WinEvent event) {
        boardView.setCellSymbol(event.getRow(), event.getColumn(), event.getPlayer().getSymbol());
        boardView.setCellBackground(event.getRow(), event.getColumn(), event.getPlayer().getPreferenceColor());
        infoBarView.setText(event.getPlayerWhoWon().getName() + " won the game!");
    }

    @Override
    public void handle(ValidMoveEvent event) {
        boardView.setCellSymbol(event.getRow(), event.getColumn(), event.getPlayer().getSymbol());
        boardView.setCellBackground(event.getRow(), event.getColumn(), event.getPlayer().getPreferenceColor());
        infoBarView.setText(event.getPlayer().getName() + " moved, it's " + event.getPlayerToPlay().getName() + "'s turn");
    }

    @Override
    public void handle(InvalidMoveEvent event) {
        infoBarView.setText("You cannot insert your pawn there!, try again");
    }

    @Override
    public void handle(DrawEvent event) {
        infoBarView.setText(event.getEventMessage());
    }

    public void newGame() {
        columnsSelectorDispatcher.disableButtons(false);
        boardView.clearGrid();
        infoBarView.clear();
        backendController.createNewGame();
    }
}
