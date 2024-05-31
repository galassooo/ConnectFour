package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayer;
import ch.supsi.connectfour.backend.business.player.ConnectFourPlayerInterface;
import ch.supsi.connectfour.backend.dataaccess.ConnectFourDataAccess;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

//OK
// Tells Jackson to use our custom deserializer
@JsonDeserialize(using = ConnectFourBusinessDeserializer.class)
public class ConnectFourBusiness implements ConnectFourBusinessInterface {
    private static final int GRID_LENGTH = 7;

    private static final int GRID_HEIGHT = 6;

    private static ConnectFourBusinessInterface instance;

    private final ConnectFourDataAccessInterface dataAccess;
    // A Path object representing the path - if available - of a save of this game

    private Path pathToSave;

    // True if the game is finished, false otherwise
    private boolean isFinished = false;

    // Stores information about the first available row in any given column
    private int[] lastPositionOccupied;

    // Represents the game board. Contains null if the cell is empty, or a reference to an instance of PlayerModel if a player is present
    private ConnectFourPlayerInterface[][] gameMatrix;

    // The first player
    private ConnectFourPlayerInterface player1;

    // The second player
    private ConnectFourPlayerInterface player2;

    // Player currently allowed to move
    private ConnectFourPlayerInterface currentPlayer;
    // Gives information about wether or not the last move operation was valid or not
    private boolean wasLastMoveValid;

    public ConnectFourBusiness(ConnectFourPlayerInterface player1, ConnectFourPlayerInterface player2) {
        if (player2 == null || player1 == null)
            throw new IllegalArgumentException("Players cannot be null");

        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = new Random().nextBoolean() ? player1 : player2;
        this.gameMatrix = new ConnectFourPlayer[GRID_HEIGHT][GRID_LENGTH];
        this.lastPositionOccupied = new int[GRID_LENGTH];
        this.dataAccess = ConnectFourDataAccess.getInstance();
    }

    /**
     * Checks if a player won
     *
     * @return true if one of the players won, false if none won yet
     */
    @JsonIgnore
    @Override
    public boolean isWin() {
        // Controlla orizzontali
        boolean won = false;
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col <= GRID_LENGTH - 4; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col].equals(gameMatrix[row][col + 1]) &&
                        gameMatrix[row][col].equals(gameMatrix[row][col + 2]) &&
                        gameMatrix[row][col].equals(gameMatrix[row][col + 3])) {
                    won = true;
                    break;
                }
            }
        }

        // Controlla verticali
        for (int col = 0; col < GRID_LENGTH; col++) {
            for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col].equals(gameMatrix[row + 1][col]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 2][col]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 3][col])) {
                    won = true;
                    break;
                }
            }
        }

        // Controlla diagonali (da sinistra a destra)
        for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
            for (int col = 0; col <= GRID_LENGTH - 4; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col].equals(gameMatrix[row + 1][col + 1]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 2][col + 2]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 3][col + 3])) {
                    won = true;
                    break;
                }
            }
        }

        // Controlla diagonali (da destra a sinistra)
        for (int row = 0; row <= GRID_HEIGHT - 4; row++) {
            for (int col = 3; col < GRID_LENGTH; col++) {
                if (gameMatrix[row][col] != null &&
                        gameMatrix[row][col].equals(gameMatrix[row + 1][col - 1]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 2][col - 2]) &&
                        gameMatrix[row][col].equals(gameMatrix[row + 3][col - 3])) {
                    won = true;
                    break;
                }
            }
        }
        return won;
    }

    /**
     * Tries to retrieve an instance of ConnectFourModel potentially associated with a file instance
     *
     * @param file a file potentially containing a valid save
     * @return an instance of ConnectFourModel if the file is valid, null otherwise
     */
    @Override
    public ConnectFourBusinessInterface getSave(@NotNull final File file) {
        return this.dataAccess.getSave(file);
    }

    @Override
    public @NotNull String getSaveName() {
        return this.pathToSave.getFileName().toString();
    }

    /**
     * Tries to persist this instance into a file in a directory
     *
     * @param outputDirectory the directory in which the file should be created
     * @param saveName        the name of the save
     * @return true if the persistence operation was successfull, false otherwise
     */
    @Override
    public boolean persist(@Nullable final File outputDirectory, @Nullable final String saveName) {
        boolean wasSaved;
        /*
            If the provided file and name are null, then it means the user wants to use the already available save
            If there is actually a path to a linked save, check if the Path actually points to an existing
            file (else it could be deleted in the meantime and lead to errors).
         */
        if (outputDirectory == null && saveName == null && this.pathToSave != null && this.pathToSave.toFile().exists()) {
            wasSaved = this.dataAccess.persist(this, this.pathToSave.toFile());
        } else {
            // TODO: the model knows the format of the file it is working with...
            this.pathToSave = Path.of(outputDirectory + File.separator + saveName + ".json");
            wasSaved = this.dataAccess.persist(this, new File(String.valueOf(this.pathToSave)));
        }
        /*
         * If there was an error while saving the game, remove the path from this instance. The update of the path
         * is performed before actually knowing if the saving operation was successfull to already include the path in
         * the serialized version of this instance.
         */
        if (outputDirectory == null && !wasSaved) {
            this.pathToSave = null;
        }
        return wasSaved;
    }

    /**
     * This method exists only to allow higher-level layers to persist an already saved game without
     * passing any parameters. The goal of this method is to hide implementation details about how
     * this persist method is implemented, aka the fact that passing null values has a specific meaning
     * for the flow of the program (information hiding)
     *
     * @return true if the game was saved correctly, false otherwise
     */
    @Override
    public boolean persist() {
        return this.persist(null, null);
    }

    @Override
    public boolean isDraw() {
        int cnt = 0;
        for (int j : lastPositionOccupied) {
            if (j == GRID_HEIGHT - 1) {
                cnt++;
            }
        }
        return cnt == GRID_LENGTH;
    }

    /**
     * Performs a deep copy of the board associated with this game
     *
     * @return a deep copy of the game board
     */
    private ConnectFourPlayerInterface[] @NotNull [] getGameMatrixDeepCopy(ConnectFourPlayerInterface[][] matrix) {
        // Get the dimensions of the original array
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Create a new array with the same dimensions
        ConnectFourPlayerInterface[][] copiedMatrix = new ConnectFourPlayer[rows][cols];

        // Deep copy each element from the original array to the new array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Perform a deep copy of the PlayerModel object
                if (matrix[i][j] != null) {
                    copiedMatrix[i][j] = (ConnectFourPlayerInterface) matrix[i][j].clone();
                }
            }
        }
        return copiedMatrix;
    }

    /**
     * Checks if it is possible to insert the token into the selected column
     *
     * @param column the column in which to check if insertion is possible
     * @return true if it is possible to insert into the column, otherwise false
     */
    @Override
    public boolean canInsert(int column) {
        // Reset it before reassigning it
        this.wasLastMoveValid = false;

        if (column < 0 || column >= GRID_LENGTH) {
            return false;
        }
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column];
        this.wasLastMoveValid = firstFreeCell < GRID_HEIGHT && firstFreeCell >= 0;
        return this.wasLastMoveValid;
    }

    /**
     * Inserts the pawn in the first available row in the given column
     *
     * @param column the column where the player wants to insert their pawn
     */
    @Override
    public void insert(int column) { //evita di avere getter e setter, nasconde implementazione
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column];
        lastPositionOccupied[column]++;
        gameMatrix[firstFreeCell][column] = currentPlayer;
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
     * Switches the player currently allowed to move
     */
    @Override
    public void switchCurrentPlayer() {
        currentPlayer = currentPlayer.equals(player2) ? player1 : player2;
    }

    // Getter and setter methods
    public boolean isFinished() {
        if (this.isFinished)
            this.wasLastMoveValid = false;

        return isFinished;
    }


    /* setters */
    public void setCurrentPlayer(@NotNull ConnectFourPlayerInterface currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayer1(@NotNull ConnectFourPlayerInterface player1) {
        this.player1 = player1;
    }

    public void setPlayer2(@NotNull ConnectFourPlayerInterface player2) {
        this.player2 = player2;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    void setLastPositionOccupied(int[] lastPositionOccupied) {
        this.lastPositionOccupied = lastPositionOccupied;
    }

    void setGameMatrix(ConnectFourPlayerInterface[][] gameMatrix) {
        this.gameMatrix = gameMatrix; //deep copy is useless, (private am)
    }

    void setPathToSave(Path pathToSave) {
        this.pathToSave = pathToSave;
    }

    void setWasLastMoveValid(boolean wasLastMoveValid) {
        this.wasLastMoveValid = wasLastMoveValid;
    }


    /* getters */
    @Override
    public ConnectFourPlayerInterface getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public ConnectFourPlayerInterface getPlayer1() {
        return player1;
    }

    @Override
    public ConnectFourPlayerInterface getPlayer2() {
        return player2; // safety copy
    }


    /**
     * Getter method for the gameMatrix field
     *
     * @return a deep copy of the original matrix
     */
    @Override
    public ConnectFourPlayerInterface[][] getGameMatrix() {
        return getGameMatrixDeepCopy(this.gameMatrix); //safety copy
    }

    public int[] getLastPositionOccupied() {
        return Arrays.copyOf(lastPositionOccupied, lastPositionOccupied.length); //safety copy
    }

    private Path getPathToSave() {
        return pathToSave;
    }

    private boolean isWasLastMoveValid() {
        return wasLastMoveValid;
    }

}
