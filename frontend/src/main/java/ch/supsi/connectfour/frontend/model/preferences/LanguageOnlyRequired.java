package ch.supsi.connectfour.frontend.model.preferences;

import javafx.event.Event;
import javafx.event.EventType;

public class LanguageOnlyRequired extends Event {
    public static final EventType<LanguageOnlyRequired> LANGUAGE_CHANGE =
            new EventType<>(Event.ANY, "LANGUAGE_CHANGE");

    private final boolean isLanguageOnlyRequired;
    public LanguageOnlyRequired(boolean value) {
        super(LANGUAGE_CHANGE);
        isLanguageOnlyRequired = value;
    }

    public boolean isLanguageOnlyRequired() {
        return isLanguageOnlyRequired;
    }
}