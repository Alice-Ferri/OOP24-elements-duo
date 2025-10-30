package it.unibo.elementsduo.controller.progresscontroller.impl;

import it.unibo.elementsduo.datasave.SaveManager; 
import it.unibo.elementsduo.model.progression.ProgressionState;

/**
 * Implementation of the ProgressionManager interface, handling the
 * progression state updates and delegation to the SaveManager.
 */
public final class ProgressionManagerImpl { 

    private ProgressionState currentState;
    private final SaveManager saveLoadManager; 

    /**
     * Creates a new ProgressionManager.
     *
     * @param manager the SaveManager instance used for persistence.
     * @param initialState the initial or loaded progression state.
     */
    public ProgressionManagerImpl(final SaveManager manager, final ProgressionState initialState) { 
        this.saveLoadManager = manager;
        this.currentState = initialState;
    }

    /**
     * Retrieves the current progression state.
     *
     * @return the current ProgressionState.
     */
    public ProgressionState getCurrentState() {
        return this.currentState;
    }

    /**
     * Updates the progression state after a level is completed and saves the game.
     *
     * @param completedLevel the number of the level completed.
     * @param timeMillis the time taken to complete the level.
     * @param gemsCollected the number of gems collected in the level.
     */
    public void levelCompleted(final int completedLevel, final long timeMillis, final int gemsCollected) { 

        final int nextLevel = completedLevel + 1; 

        this.currentState.addLevelCompletionTime(completedLevel, timeMillis);
        this.currentState.setCollectedGems(this.currentState.getCollectedGems() + gemsCollected);
        this.currentState.setCurrentLevel(nextLevel);

        this.saveLoadManager.saveGame(this.currentState);
    }

    /**
     * Saves the current progression state to the file system.
     */
    public void saveGame() {
        this.saveLoadManager.saveGame(this.currentState);
    }
}
