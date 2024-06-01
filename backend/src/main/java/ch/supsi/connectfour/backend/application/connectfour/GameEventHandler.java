package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.MoveEvent;

/**
 * Defines what functionalities a generic handler of game events should provide
 */
public interface GameEventHandler {
    void handle(MoveEvent event);
}
