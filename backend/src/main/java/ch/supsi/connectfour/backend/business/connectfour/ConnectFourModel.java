package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.dataaccess.ConnectFourDataAccess;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;


@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public final class ConnectFourModel implements ConnectFourBusinessInterface {

    @JsonIgnore
    private static ConnectFourBusinessInterface instance;
    @JsonIgnore
    private final ConnectFourDataAccessInterface dataAccess;

    @JsonIgnore
    private static final int GRID_LENGTH = 7;
    @JsonIgnore
    private static final int GRID_HEIGHT = 6;

    private Path pathToSave = null;

    /**
     * restituisce true se la partita è terminata, altrimenti false.
     */
    private boolean isFinished = false;

    /**
     * permette di ottenere per ogni colonna la prima posizione libera disponibile
     */
    private int[] lastPositionOccupied;

    /**
     * player1 se è presente elemento del giocatore1, idem per il giocatore 2, null se vuoto
     */
    private PlayerModel[][] gameMatrix;

    /**
     * Giocatore 1
     */
    private PlayerModel player1;

    /**
     * Giocatore 2
     */
    private PlayerModel player2;

    /**
     * Giocatore attualmente in turno
     */
    private PlayerModel currentPlayer;

    @JsonCreator
    private ConnectFourModel(
            @JsonProperty(value = "isFinished") final boolean isFinished,
            @JsonProperty(value = "lastPositionOccupied") final int[] lastPositionOccupied,
            @JsonProperty(value = "gameMatrix") final PlayerModel[][] gameMatrix,
            @JsonProperty(value = "player1") final PlayerModel player1,
            @JsonProperty(value = "player2") final PlayerModel player2,
            @JsonProperty(value = "pathToSave") final Path pathToSave) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameMatrix = gameMatrix;
        this.lastPositionOccupied = lastPositionOccupied;
        this.isFinished = isFinished;
        this.pathToSave = pathToSave;
        this.dataAccess = ConnectFourDataAccess.getInstance();
    }

    public ConnectFourModel(PlayerModel player1, PlayerModel player2) {
        if (player2 == null || player1 == null)
            throw new IllegalArgumentException("Players cannot be null");
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = new Random().nextBoolean() ? player1 : player2;
        this.gameMatrix = new PlayerModel[GRID_HEIGHT][GRID_LENGTH];
        this.lastPositionOccupied = new int[GRID_LENGTH];
        this.dataAccess = ConnectFourDataAccess.getInstance();

    }

    /**
     * controlla se allo stato della chiamata la partita è vinta
     * @return true se un giocatore ha vinto
     */
    //TODO trovare approccio migliore della brute force
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


    //getters and setters
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public ConnectFourModel getSave(@NotNull final File file) {
        return this.dataAccess.getSave(file);
    }

    @Override
    public boolean persist(@Nullable final File outputDirectory, @Nullable final String name) {
        boolean wasSaved = false;
        Path path = null;
        /*
            If the provided file and name are null, then it means the user wants to use the already available save
            If there is actually a path to a linked save, check if the Path actually points to an existing
            file (else it could be deleted in the meantime and lead to errors).
         */
        if (outputDirectory == null && name == null && this.pathToSave != null && this.pathToSave.toFile().exists()) {
            wasSaved = this.dataAccess.persist(this, this.pathToSave.toFile());
        } else {
            path = Path.of(outputDirectory + File.separator + name + ".json");
            wasSaved = this.dataAccess.persist(this, new File(String.valueOf(path)));
        }
        // If the user specified an output directory and this game was successfully saved, then update the path to the save
        // The naming convention for the saves was chosen arbitrarily and consists of
        if (outputDirectory != null && wasSaved) {
            this.pathToSave = path;
        }
        return wasSaved;
    }

    @Override
    public boolean wasSavedAs() {
        return this.pathToSave != null;
    }

    @Override
    public PlayerModel[][] getGameMatrix() {
        // TODO: consider a better approach like a defensive copy to prevent misuse outside
        return this.gameMatrix;
    }

    public PlayerModel getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Controlla se è possibile inserire la pedina nella colonna selezionata
     * @param column colonna nel quale si vuole controllare se l'inserimento è possibile
     * @return true se è possibile inserire nella colonna, altrimenti false
     */
    public boolean canInsert(int column) {
        if (column < 0 || column >= GRID_LENGTH)
            return false;
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column]; //post increment
        return firstFreeCell < GRID_HEIGHT && firstFreeCell >= 0;
    }

    /**
     * Inserisce la pedina nella prima posizione disponibile nella colonna
     * @param column colonna nel quale si intende inserire la pedina
     */
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
     *
     * @param column colonna della quale si vuole ottenere l'ultima riga occupata
     * @return l'indice dell'ultima riga occupata in quella colonna
     */
    public int getLastPositioned(int column) {
        return GRID_HEIGHT - lastPositionOccupied[column];
    }

    /**
     * Effettua il cambio di giocatore in turno
     */
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
