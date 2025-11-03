package it.unibo.elementsduo.controller.progresscontroller.impl;

import it.unibo.elementsduo.datasave.SaveManager; 
import it.unibo.elementsduo.model.progression.ProgressionState;

/**
 * Implementation of the ProgressionManager interface, handling the
 * progression state updates and delegation to the SaveManager.
 */
public final class ProgressionManagerImpl { 

    private final ProgressionState currentState;
    private final SaveManager saveLoadManager; 

    /**
     * Creates a new ProgressionManager.
     *
     * @param manager the SaveManager instance used for persistence.
     * @param initialState the initial or loaded progression state.
     */
    public ProgressionManagerImpl(final SaveManager manager, final ProgressionState initialState) { 
        this.saveLoadManager = manager;
        this.currentState = new ProgressionState(initialState);
    }

    /**
     * Retrieves the current progression state.
     *
     * @return the current ProgressionState.
     */
    public ProgressionState getCurrentState() {
        return new ProgressionState(this.currentState);
    }

    /**
     * Updates the progression state after a level is completed and saves the game.
     *
     * @param completedLevel the number of the level completed.
     * @param timeSeconds the time taken to complete the level.
     * @param missionCompleted if the mission is completed.
     */
    public void levelCompleted(final int completedLevel, final double timeSeconds, final boolean missionCompleted) { 

        final int nextLevel = completedLevel + 1; 

        this.currentState.addLevelCompletionTime(completedLevel, timeSeconds, missionCompleted);
        this.currentState.setCurrentLevel(nextLevel);

        this.saveLoadManager.saveGame(this.currentState);
    }

    /**
     * Saves the current progression state to the file system.
     */
    public void saveGame() {
        this.saveLoadManager.saveGame(this.currentState);
    }

    /**
     * Sets the current level the player is on.
     *
     * @param currentLevel the new current level number.
     */
    public void setCurrentLevel(final int levelnumber) { 
        this.currentState.setCurrentLevel(levelnumber); 
    }
}
