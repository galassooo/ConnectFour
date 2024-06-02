package ch.supsi.connectfour.frontend.model.about;

import ch.supsi.connectfour.frontend.model.Translatable;


/**
 * Defines the behaviour that a generic about model should expose to the view and controller
 */
public interface IAboutModel extends Translatable {
    String getDevelopers();

    String getVersion();

    String getDate();

}