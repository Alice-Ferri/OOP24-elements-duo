package it.unibo.elementsduo.controller.progresscontroller.api;

import it.unibo.elementsduo.model.progression.ProgressionState;

/**
 * Manages the player's progression through the game, including saving, loading,
 * and updating the state after completing a level.
 */
public interface ProgressionManager {

    /**
     * Retrieves the current progression state of the game.
     *
     * @return the current ProgressionState object.
     */
    ProgressionState getCurrentState();

    /**
     * Updates the progression state after a level is successfully completed.
     *
     * @param completedLevel the number of the level that was just completed.
     * @param timeMillis the time taken to complete the level, in milliseconds.
     * @param gemsCollected the number of gems collected in the completed level.
     */
    void levelCompleted(int completedLevel, long timeMillis, int gemsCollected);
}

