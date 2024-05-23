package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Random;


public final class ConnectFourModel implements ConnectFourBusinessInterface {
    private static ConnectFourDataAccessInterface dataAccess;

    private static final int GRID_LENGTH = 7;
    private static final int GRID_HEIGHT = 6;

    /**
     * restituisce true se la partita è terminata, altrimenti false.
     */
    private boolean isFinished = false;

    /**
     * permette di ottenere per ogni colonna la prima posizione libera disponibile
     */

    private final int[] lastPositionOccupied = new int[GRID_LENGTH];

    /**
     * player1 se è presente elemento del giocatore1, idem per il giocatore 2, null se vuoto
     */
    private final PlayerModel[][] gameMatrix = new PlayerModel[GRID_HEIGHT][GRID_LENGTH];

    /**
     * Giocatore 1
     */
    private final PlayerModel player1;
    /**
     * Giocatore 2
     */
    private final PlayerModel player2;

    /**
     * Giocatore attualmente in turno
     */
    private PlayerModel currentPlayer;

    public ConnectFourModel(PlayerModel player1, PlayerModel player2) {
        if (player2 == null || player1 == null)
            throw new IllegalArgumentException("Players cannot be null");
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = new Random().nextBoolean() ? player1 : player2;

    }

    /**
     * controlla se allo stato della chiamata la partita è vinta
     *
     * @return true se un giocatore ha vinto
     */
    //TODO trovare approccio migliore della brute force
    @Override
    public boolean checkWin() {
        // Controlla orizzontali
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col <= GRID_LENGTH - 4; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col] == gameMatrix[row][col + 1] &&
                        gameMatrix[row][col] == gameMatrix[row][col + 2] &&
                        gameMatrix[row][col] == gameMatrix[row][col + 3]) {
                    return true;
                }
            }
        }

        // Controlla verticali
        for (int col = 0; col < GRID_LENGTH; col++) {
            for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col] == gameMatrix[row + 1][col] &&
                        gameMatrix[row][col] == gameMatrix[row + 2][col] &&
                        gameMatrix[row][col] == gameMatrix[row + 3][col]) {
                    return true;
                }
            }
        }

        // Controlla diagonali (da sinistra a destra)
        for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
            for (int col = 0; col <= GRID_LENGTH - 4; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col] == gameMatrix[row + 1][col + 1] &&
                        gameMatrix[row][col] == gameMatrix[row + 2][col + 2] &&
                        gameMatrix[row][col] == gameMatrix[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        // Controlla diagonali (da destra a sinistra)
        for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
            for (int col = 3; col < GRID_LENGTH; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col] == gameMatrix[row + 1][col - 1] &&
                        gameMatrix[row][col] == gameMatrix[row + 2][col - 2] &&
                        gameMatrix[row][col] == gameMatrix[row + 3][col - 3]) {
                    return true;
                }
            }
        }

        return false;

    }


    @Override
    @Contract(pure = true)
    public boolean isDraw() {
        int cnt = 0;
        for (int j : lastPositionOccupied) {
            if (j == GRID_HEIGHT - 1) {
                cnt++;
            }
        }
        return cnt == GRID_LENGTH;
    }

    //getters and setters
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public PlayerModel getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public PlayerModel getPlayer1() {
        return (PlayerModel) player1.clone();
    }

    @Override
    public PlayerModel getPlayer2() {
        return (PlayerModel) player2.clone();
    }

    /**
     * Controlla se è possibile inserire la pedina nella colonna selezionata
     *
     * @param column colonna nel quale si vuole controllare se l'inserimento è possibile
     * @return true se è possibile inserire nella colonna, altrimenti false
     */
    @Override
    @Contract(pure = true)
    public boolean canInsert(int column) {
        if (column < 0 || column >= GRID_LENGTH)
            return false;
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column];
        return firstFreeCell < GRID_HEIGHT && firstFreeCell >= 0;
    }

    /**
     * Inserisce la pedina nella prima posizione disponibile nella colonna
     *
     * @param column colonna nel quale si intende inserire la pedina
     */
    @Override
    public void insert(int column) { //evita di avere getter e setter, nasconde implementazione
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column];
        lastPositionOccupied[column]++;
        gameMatrix[firstFreeCell][column] = currentPlayer;
    }

    @Contract(pure = true) //indica che il metodo non modifica nessun valore
    public @Nullable PlayerModel getCell(int row, int column) {
        if (column < 0 || column >= GRID_LENGTH || row < 0 || row >= GRID_HEIGHT)
            return null;
        return gameMatrix[row][column];
    }

    /**
     * @param column colonna della quale si vuole ottenere l'ultima riga occupata
     * @return l'indice dell'ultima riga occupata in quella colonna
     */
    @Override
    public int getLastPositioned(int column) {
        return GRID_HEIGHT - lastPositionOccupied[column];
    }

    /**
     * Effettua il cambio di giocatore in turno
     */
    @Override
    public void switchCurrentPlayer() {
        currentPlayer = currentPlayer.equals(player2) ? player1 : player2;
    }

    //solo per test
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_LENGTH; col++) {
                PlayerModel cell = gameMatrix[row][col];
                if (cell == null) {
                    sb.append("0 ");
                } else if (cell.equals(player1)) {
                    sb.append("1 ");
                } else if (cell.equals(player2)) {
                    sb.append("2 ");
                }
            }
            sb.append("\n");  // Nuova linea alla fine di ogni riga
        }
        return sb.toString();
    }

}
