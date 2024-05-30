package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;

import java.util.Objects;

public abstract class GameEvent implements GameEventInterface {
    private final static TranslationsController translations = TranslationsController.getInstance();
    private final String eventMessage;

    private final String eventLogMessage;

    public GameEvent(String eventName, String eventLogMessage) {
        this.eventMessage = eventName;
        this.eventLogMessage = eventLogMessage;
    }

    protected static TranslationsController getTranslator() {
        return translations;
    }

    public final String getEventMessage() {
        return eventMessage;
    }

    public final String getEventLogMessage() {
        return eventLogMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameEvent gameEvent)) return false;
        return Objects.equals(getEventMessage(), gameEvent.getEventMessage()) && Objects.equals(getEventLogMessage(), gameEvent.getEventLogMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventMessage(), getEventLogMessage());
    }
}
