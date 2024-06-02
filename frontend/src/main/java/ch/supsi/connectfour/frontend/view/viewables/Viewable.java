package ch.supsi.connectfour.frontend.view.viewables;

import ch.supsi.connectfour.backend.application.event.GameEvent;

/**
 * Defines the behaviour that a generic view should expose to controllers. This is used for the views
 * used by the ConnectFourFrontendController to constantly display information to user in response to user events
 */
public interface Viewable {
    // Defines a method that clears the view, bringing its state back to when it was first initialized on startup
    void clear();

    void show(GameEvent event);
}