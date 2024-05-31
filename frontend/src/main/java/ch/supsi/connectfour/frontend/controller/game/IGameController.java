package ch.supsi.connectfour.frontend.controller.game;

/**
 * Defines the behaviour that a generic game controller should expose to the dispatcher
 */
public interface IGameController {
    void manageNew();

    void manageOpen();

    void manageSave();

    void manageSaveAs();
}
