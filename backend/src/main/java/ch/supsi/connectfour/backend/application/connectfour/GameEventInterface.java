package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.connectfour.GameEventHandler;

public interface GameEventInterface {
    void handle(GameEventHandler handler);
}
