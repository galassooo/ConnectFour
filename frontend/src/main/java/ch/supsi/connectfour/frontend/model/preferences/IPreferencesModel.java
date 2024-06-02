package ch.supsi.connectfour.frontend.model.preferences;

import ch.supsi.connectfour.frontend.model.Translatable;

/**
 * Defines the behaviour that a generic preferences model should expose to the view and controller
 */
public interface IPreferencesModel extends Translatable {
    void setLanguageOnlyRequested(boolean b);

}