package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.*;

public interface GameEventHandler {
    void handle(MoveEvent event);
}
