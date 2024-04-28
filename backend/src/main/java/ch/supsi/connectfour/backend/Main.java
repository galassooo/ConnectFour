package ch.supsi.connectfour.backend;

import ch.supsi.connectfour.backend.model.Match;
import ch.supsi.connectfour.backend.model.Player;

//questa classe serve esclusivamente per testare il backend in fase di produzione, va eliminata alla fine!!!
public class Main {
    public static void main(String[] args) {
        Player p = new Player("giovanni", 80);
        System.out.println(p);
        Player p1 = new Player("", -2);
        System.out.println(p1);

        Match m = new Match(p, p1);
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
