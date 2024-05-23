package ch.supsi.connectfour.backend.application.event;

public abstract class GameEvent implements GameEventInterface {
    private final String eventMessage;

    public GameEvent(String eventName) {
        this.eventMessage = eventName;
    }

    public String getEventMessage() {
        return eventMessage;
    }
}
