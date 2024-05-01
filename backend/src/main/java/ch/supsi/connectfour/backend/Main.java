package ch.supsi.connectfour.backend;

import ch.supsi.connectfour.backend.business.connectfour.ConnectFourModel;
import ch.supsi.connectfour.backend.business.player.PlayerModel;

//questa classe serve esclusivamente per testare il backend in fase di produzione, va eliminata alla fine!!!
public class Main {
    public static void main(String[] args) {
        PlayerModel p = new PlayerModel("giovanni", 80);
        System.out.println(p);
        PlayerModel p1 = new PlayerModel("", -2);
        System.out.println(p1);

        ConnectFourModel m = new ConnectFourModel(p, p1);
        System.out.println(m);

        m.setCell(p1, 0);
        m.setCell(p1, 1);
        m.setCell(p,0);
        m.setCell(p, 6);
        m.setCell(p, 6);
        m.setCell(p1, 6);

        System.out.println();
        System.out.println(m);
    }
}
