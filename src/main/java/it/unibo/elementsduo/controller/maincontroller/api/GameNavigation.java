package it.unibo.elementsduo.controller.maincontroller.api;

/**
 * Defines navigation actions available from the in-game screen,
 * such as when the game is over.
 */
public interface GameNavigation {

    /**
     * Exits the current game and returns to the main menu.
     */
    void goToMenu();

    /**
     * Exits the current game and returns to the level selection screen.
     */
    void goToLevelSelection();

    /**
     * Restarts the currently active level from the beginning.
     */
    void restartCurrentLevel();

}
