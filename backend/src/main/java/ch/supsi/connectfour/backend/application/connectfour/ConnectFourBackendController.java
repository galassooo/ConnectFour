package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.preferences.PreferencesBusinessInterface;
import ch.supsi.connectfour.backend.application.serialization.SerializationBusinessInterface;
import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.movedata.MoveData;
import ch.supsi.connectfour.backend.business.player.PlayerModel;
import org.jetbrains.annotations.Nullable;

public class ConnectFourBackendController {
    private static ConnectFourBackendController instance;
    private static PreferencesBusinessInterface preferences;
    private static SerializationBusinessInterface serialization;
    private static ConnectFourBusinessInterface currentMatch;


    //singleton pattern
    public static ConnectFourBackendController getInstance() {
        if (instance == null) {
            instance = new ConnectFourBackendController();
        }
        return instance;
    }

    /**
     * Serve per gestire la mossa del giocatore
     * @param column colonna nel quale il giocatore intende inserire la pedina
     * @return un oggetto contenente i dati relativi alla mossa
     */
    public @Nullable MoveData playerMove(int column){
        if(currentMatch == null){
            currentMatch = new ConnectFourModel(new PlayerModel("p1"), new PlayerModel("p2"));
        }
        if(currentMatch.canInsert(column)){
            currentMatch.insert(column);
            MoveData data;
            if(currentMatch.checkWin()){
                System.out.println("player ha vinto");
                data = new MoveData(currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column), true);
            }else {
                data = new MoveData(currentMatch.getCurrentPlayer(), column, currentMatch.getLastPositioned(column), false);
                currentMatch.switchCurrentPlayer();
                System.out.println(currentMatch);
                System.out.println("player ha mosso e il turno è cambiato");
            }
            return data;
        }else
            return null;
    }
}
