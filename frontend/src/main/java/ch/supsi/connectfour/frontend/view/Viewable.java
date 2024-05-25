package ch.supsi.connectfour.frontend.view;

import ch.supsi.connectfour.backend.application.event.GameEvent;
import ch.supsi.connectfour.backend.application.event.MoveEvent;

public interface Viewable {
    // Defines a method that clears the view, bringing its state back to when it was first initialized on startup
    void clear();

    void show(GameEvent event);
}
