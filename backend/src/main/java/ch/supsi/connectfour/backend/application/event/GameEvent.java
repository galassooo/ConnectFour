package ch.supsi.connectfour.backend.application.event;

import ch.supsi.connectfour.backend.application.translations.TranslationsController;

public abstract class GameEvent implements GameEventInterface {
    private final static TranslationsController translations = TranslationsController.getInstance();
    private final String eventMessage;

    private final String eventLogMessage;

    public GameEvent(String eventName, String eventLogMessage) {
        this.eventMessage = eventName;
        this.eventLogMessage = eventLogMessage;
    }

    public final String getEventMessage() {
        return eventMessage;
    }

    protected static TranslationsController getTranslator() {
        return translations;
    }

    public final String getEventLogMessage() {
        return eventLogMessage;
    }
}
