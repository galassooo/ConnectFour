package ch.supsi.connectfour.backend.application.event;


public interface GameEventInterface {
    void handle(GameEventHandler handler);
}
