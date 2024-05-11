package ch.supsi.connectfour.backend.business.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.ConnectFourBusinessInterface;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
public final class ConnectFourModel implements ConnectFourBusinessInterface {

    @JsonIgnore
    private static ConnectFourModel instance;
    @JsonIgnore
    private static ConnectFourDataAccessInterface dataAccess;;

    @JsonIgnore
    private static final int GRID_LENGTH = 7;
    @JsonIgnore
    private static final int GRID_HEIGHT = 6;

    //todo: valutare di spostare isFinished in una classe di logica come un controller?
    /**
     * restituisce true se la partita è terminata, altrimenti false.
     */
    private boolean isFinished = false;

    /**
     * permette di ottenere per ogni colonna la prima posizione libera disponibile
     */
    private final int[] lastPositionOccupied;

    /**
     * player1 se è presente elemento del giocatore1, idem per il giocatore 2, null se vuoto
     */
    private final PlayerModel[][] gameMatrix;

    private final PlayerModel player1;

    private final PlayerModel player2;

    @JsonCreator
    private ConnectFourModel(@JsonProperty(value = "isFinished") final boolean isFinished, @JsonProperty(value = "lastPositionOccupied") final int[] lastPositionOccupied, @JsonProperty(value = "gameMatrix") final PlayerModel[][] gameMatrix, @JsonProperty(value = "player1") final PlayerModel player1, @JsonProperty(value = "player2") final PlayerModel player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameMatrix = gameMatrix;
        this.lastPositionOccupied = lastPositionOccupied;
        this.isFinished = isFinished;
    }

    public ConnectFourModel(PlayerModel player1, PlayerModel player2) {
        if(player2 == null || player1 == null)
            throw new IllegalArgumentException("Players cannot be null");
        this.gameMatrix = new PlayerModel[GRID_HEIGHT][GRID_LENGTH];
        this.lastPositionOccupied = new int[GRID_LENGTH];
        this.player1 = player1;
        this.player2 = player2;
    }
    public ConnectFourModel(final PlayerModel player1, final PlayerModel player2, final PlayerModel[][] gameMatrix) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameMatrix = gameMatrix;
        this.lastPositionOccupied = new int[GRID_LENGTH];
    }


    //getters and setters
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean setCell(@Nullable PlayerModel player, int column){ //evita di avere getter e setter, nasconde implementazione
        if(column < 0 || column >= GRID_LENGTH)
            return false;

        int firstFreeCell = lastPositionOccupied[column]++; //post increment
        gameMatrix[firstFreeCell][column] = player;
        return true;
    }

    @Contract(pure = true) //indica che il metodo non modifica nessun valore
    public @Nullable PlayerModel getCell(int row, int column){
        if(column < 0 || column >= GRID_LENGTH || row < 0 || row >= GRID_HEIGHT)
            return null;
        return gameMatrix[row][column];
    }


    //solo per test
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.gameMatrix == null)
            return "";

        for (int row = GRID_HEIGHT - 1; row >= 0; row--) {  //scorre le righe al contrario per avere indice 00 in basso a sinistra
            for (int col = 0; col < GRID_LENGTH; col++) {  //scorre le colonne normalmente
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
