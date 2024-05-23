package ch.supsi.connectfour.backend.application.connectfour;

import ch.supsi.connectfour.backend.application.event.DrawEvent;
import ch.supsi.connectfour.backend.application.event.InvalidMoveEvent;
import ch.supsi.connectfour.backend.application.event.ValidMoveEvent;
import ch.supsi.connectfour.backend.application.event.WinEvent;

public interface GameEventHandler {
    void handle(ValidMoveEvent event);
    void handle(WinEvent event);
    void handle(InvalidMoveEvent event);
    void handle(DrawEvent event);

}
