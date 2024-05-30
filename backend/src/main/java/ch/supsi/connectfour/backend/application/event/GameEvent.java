package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.translations.TranslationsApplication;

import java.util.Objects;

public abstract class GameEvent implements GameEventInterface {

    /* fields */
    private final static TranslationsApplication translations = TranslationsApplication.getInstance();
    private final String eventMessage;
    private final String eventLogMessage;

    /* constructors */
    public GameEvent(String eventName, String eventLogMessage) {
        this.eventMessage = eventName;
        this.eventLogMessage = eventLogMessage;
    }

    protected static TranslationsApplication getTranslator() {
        return translations;
    }

    /* getters */
    public final String getEventMessage() {
        return eventMessage;
    }

    public final String getEventLogMessage() {
        return eventLogMessage;
    }

    /* overrides */
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
