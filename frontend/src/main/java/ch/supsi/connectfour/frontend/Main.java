package ch.supsi.connectfour.frontend;

import ch.supsi.connectfour.backend.application.symbol.SymbolProvider;
import ch.supsi.connectfour.backend.business.symbols.Symbol;
import ch.supsi.connectfour.backend.business.symbols.SymbolInterface;

import java.net.URL;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MainFx.main(args);
    }
}
/*TODO

1) eliminare unused imports
2) rendere tutte le classi estendibili ma non modificabili
3) se sostituisco il modello della partita con ad esempio il gioco del tris funziona?
4) sistemare i simboli
5) override dell'equals e del hashcode

7) rimuovere campi inutili (tipo quelli in mainFX)
 */