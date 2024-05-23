package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import ch.supsi.connectfour.backend.dataaccess.ConnectFourDataAccess;
import com.fasterxml.jackson.annotation.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class ConnectFourModel implements ConnectFourBusinessInterface {
    /*
        While technically not essential because Jackson considers non-annoted fields as
        included by default, I decided to explicitly annotate both ignored and included
        fields for serialization to state as clearly as possible what is serialized and what
        is not
     */
    // TODO: Does it make sense to implement the singleton pattern for the model????
    @JsonIgnore
    private static ConnectFourBusinessInterface instance;
    @JsonIgnore
    private final ConnectFourDataAccessInterface dataAccess;

    @JsonIgnore
    private static final int GRID_LENGTH = 7;
    @JsonIgnore
    private static final int GRID_HEIGHT = 6;
    // A Path object representing the path - if available - of a save of this game
    @JsonInclude
    private Path pathToSave;

    // True if the game is finished, false otherwise
    @JsonInclude
    private boolean isFinished = false;

    // Stores information about the first available row in any given column
    @JsonInclude
    private int[] lastPositionOccupied;

    // Represents the game board. Contains null if the cell is empty, or a reference to an instance of PlayerModel if a player is present
    @JsonInclude
    private PlayerModel[][] gameMatrix;

    // The first player
    @JsonInclude
    private PlayerModel player1;

    // The second player
    @JsonInclude
    private PlayerModel player2;

    // Player currently allowed to move
    @JsonInclude
    private PlayerModel currentPlayer;
    // Gives information about wether or not the last move operation was valid or not
    @JsonInclude
    private boolean wasLastMoveValid;

    /*
    This constructor is required in order for the Jackson library to serialize the game. It should not be used elsewhere nor modified.
    All components that are not serialized must be initialized here.
    */
    @JsonCreator
    private ConnectFourModel() {
        this.dataAccess = ConnectFourDataAccess.getInstance();
    }

    @JsonIgnore
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
     * Checks if a player has won
     *
     * @return true if one of the players won, false if none won yet
     */
    //TODO trovare approccio migliore della brute force
    @Override
    public boolean checkWin() {
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

    @JsonIgnore
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

    /**
     * Used to get information about wether or not this instance was ever saved as before or not
     *
     * @return true if this instance was saved as, false otherwise
     */
    @Override
    public boolean wasSavedAs() {
        return this.pathToSave != null;
    }

    @JsonIgnore
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

    // TODO: consider if it's worth doing... not sure if we are supposed to trust that the controller knows what it is doing or if we should be cautious and make defensive copies / prevent random modifications from the outside
    private PlayerModel[][] getGameMatrixDeepCopy() {
        // Get the dimensions of the original array
        int rows = this.gameMatrix.length;
        int cols = gameMatrix[0].length;

        // Create a new array with the same dimensions
        PlayerModel[][] copiedMatrix = new PlayerModel[rows][cols];

        // Deep copy each element from the original array to the new array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Perform a deep copy of the PlayerModel object
                if (this.gameMatrix[i][j] != null) {
                    copiedMatrix[i][j] = (PlayerModel) this.gameMatrix[i][j].clone();
                }
            }
        }
        return copiedMatrix;
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

    @JsonIgnore
    @Override
    public boolean isLastMoveValid() {
        return wasLastMoveValid;
    }

    /**
     * Controlla se è possibile inserire la pedina nella colonna selezionata
     *
     * @param column colonna nel quale si vuole controllare se l'inserimento è possibile
     * @return true se è possibile inserire nella colonna, altrimenti false
     */
    @Override
    public boolean canInsert(int column) {
        // Reset it before reassigning it
        this.wasLastMoveValid = false;

        if (column < 0 || column >= GRID_LENGTH) {
            return false;
        }
        int firstFreeCell = GRID_HEIGHT - 1 - lastPositionOccupied[column]; //post increment
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

    // TODO: get rid of this once the project is ready to be submitted
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_LENGTH; col++) {
                PlayerModel cell = gameMatrix[row][col];
                if (cell == null) {
                    sb.append("0 ");
                } else if (cell.equals(player1)) {
                    // TODO: EVENTUALLY uncomment and go back to 1s and 2s
                    //sb.append("1 ");
                    sb.append("P1").append(" ");
                } else if (cell.equals(player2)) {
                    //sb.append("2 ");
                    sb.append("P2").append(" ");
                }
            }
            sb.append("\n");  // Nuova linea alla fine di ogni riga
        }
        sb.append("isFinished:").append(this.isFinished);
        sb.append("\nwasLastMoveValid:").append(this.wasLastMoveValid);
        sb.append("\npathToSave:").append(this.pathToSave);
        sb.append("\ncurrentPlayer:").append(this.currentPlayer);
        return sb.toString();
    }

    // Getter and setter methods
    public boolean isFinished() {
        // TODO: FA SCHIFO
        if (this.isFinished)
            this.wasLastMoveValid = false;

        return isFinished;
    }

    @JsonSetter
    @JsonIgnore
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @JsonGetter
    public int[] getLastPositionOccupied() {
        return Arrays.copyOf(lastPositionOccupied, lastPositionOccupied.length);
    }

    /**
     * Getter method for the gameMatrix field
     *
     * @return a deep copy of the original matrix
     */
    @JsonGetter
    @Override
    public PlayerModel[][] getGameMatrix() {
        return this.getGameMatrixDeepCopy();
    }

    @JsonGetter
    private Path getPathToSave() {
        return pathToSave;
    }

    @JsonSetter
    private void setPathToSave(Path pathToSave) {
        this.pathToSave = pathToSave;
    }

    @JsonSetter
    private void setLastPositionOccupied(int[] lastPositionOccupied) {
        this.lastPositionOccupied = lastPositionOccupied;
    }

    @JsonSetter
    private void setGameMatrix(PlayerModel[][] gameMatrix) {
        this.gameMatrix = gameMatrix;
    }

    @JsonSetter
    private void setPlayer1(PlayerModel player1) {
        this.player1 = player1;
    }

    @JsonSetter
    private void setPlayer2(PlayerModel player2) {
        this.player2 = player2;
    }

    @JsonSetter
    private void setCurrentPlayer(PlayerModel currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @JsonGetter
    private boolean isWasLastMoveValid() {
        return wasLastMoveValid;
    }

    @JsonSetter
    private void setWasLastMoveValid(boolean wasLastMoveValid) {
        this.wasLastMoveValid = wasLastMoveValid;
    }
}
