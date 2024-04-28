package ch.supsi.connectfour.backend.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public final class Match implements Serializable {
    @Serial
    private static final long serialVersionUID = 8331237173987626361L;

    private static final int GRID_LENGTH = 7;
    private static final int GRID_HEIGHT = 6;

    //todo: valutare di spostare isFinished in una classe di logica come un controller?
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
    private final Player[][] gameMatrix = new Player[GRID_HEIGHT][GRID_LENGTH];

    private final Player player1;
    private final Player player2;

    public Match(Player player1, Player player2) {
        if(player2 == null || player1 == null)
            throw new IllegalArgumentException("Players cannot be null");
        this.player1 = player1;
        this.player2 = player2;
    }

    //getters and setters
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean setCell(@Nullable Player player, int column){ //evita di avere getter e setter, nasconde implementazione
        if(column < 0 || column >= GRID_LENGTH)
            return false;

        int firstFreeCell = lastPositionOccupied[column]++; //post increment
        gameMatrix[firstFreeCell][column] = player;
        return true;
    }

    @Contract(pure = true) //indica che il metodo non modifica nessun valore
    public @Nullable Player getCell(int row, int column){
        if(column < 0 || column >= GRID_LENGTH || row < 0 || row >= GRID_HEIGHT)
            return null;
        return gameMatrix[row][column];
    }


    //solo per test
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = GRID_HEIGHT - 1; row >= 0; row--) {  //scorre le righe al contrario per avere indice 00 in basso a sinistra
            for (int col = 0; col < GRID_LENGTH; col++) {  //scorre le colonne normalmente
                Player cell = gameMatrix[row][col];
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
