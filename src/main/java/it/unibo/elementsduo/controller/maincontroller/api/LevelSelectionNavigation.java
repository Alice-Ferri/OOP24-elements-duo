package it.unibo.elementsduo.controller.maincontroller.api;

/**
 * Defines navigation actions available from the Level Selection screen.
 */
public interface LevelSelectionNavigation {

    /**
     * Navigates back to the main menu.
     */
    void goToMenu();

    /**
     * Starts the game at the specified level.
     *
     * @param levelNumber The number of the level to start.
     */
    void startGame(int levelNumber);

}
