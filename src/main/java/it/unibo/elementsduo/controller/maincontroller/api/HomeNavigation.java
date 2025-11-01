package it.unibo.elementsduo.controller.maincontroller.api;

/**
 * Defines navigation actions available from the Home screen.
 */
public interface HomeNavigation {

    /**
     * Navigates to the level selection screen.
     */
    void goToLevelSelection();

    /**
     * Quits the application.
     */
    void quitGame();

    /**
     * Starts a new game, erasing any previous progress.
     */
    void startNewGame();

    /**
     * Loads a previously saved game.
     */
    void loadSavedGame();

    /**
     * Show a little guide of the game.
     */
    void gameGuide();
}
