package it.unibo.elementsduo.model.progression;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player's progression state in the game, including unlocked levels,
 * best times, and maximum gems collected per level.
 */
public final class ProgressionState {

    private int currentLevel; 
    private final Map<Integer, Double> levelCompletionTimes = new HashMap<>(); 
    private final Map<Integer, Integer> levelGemsCollected = new HashMap<>();

    /**
     * Default constructor for ProgressionState.
     */
    public ProgressionState() { 

    }

    /**
     * Constructor for a new ProgressionState with initial values.
     *
     * @param currentLevel the starting level.
     */
    public ProgressionState(final int currentLevel, final int collectedGems) {
        this.currentLevel = currentLevel;
    }

    /**
     * Updates the progression data for a given level.
     * Saves the new time if it is a record (lower time).
     * Saves the new gem count if it is a new maximum for that level (higher count).
     *
     * @param levelNumber the number of the level completed.
     * @param timeSeconds the time taken to complete the level, in milliseconds.
     * @param gemsCollected the number of gems collected in the level.
     */
    public void addLevelCompletionTime(final int levelNumber, final double timeSeconds, final int gemsCollected) {

        final boolean isNewBestTime = !this.levelCompletionTimes.containsKey(levelNumber) 
                                   || timeSeconds < this.levelCompletionTimes.get(levelNumber);
        
        if (isNewBestTime) {
            this.levelCompletionTimes.put(levelNumber, timeSeconds);
        }

        final boolean isNewMaxGems = !this.levelGemsCollected.containsKey(levelNumber) 
                                  || gemsCollected > this.levelGemsCollected.get(levelNumber);

        if (isNewMaxGems) {
            this.levelGemsCollected.put(levelNumber, gemsCollected);
        }
    }

    /**
     * Gets the current level the player is on.
     *
     * @return the current level number.
     */
    public int getCurrentLevel() { 
        return this.currentLevel; 
    }

    /**
     * Sets the current level the player is on.
     *
     * @param currentLevel the new current level number.
     */
    public void setCurrentLevel(final int currentLevel) { 
        this.currentLevel = currentLevel; 
    }

    /**
     * Gets the map of the best completion times. Keys are level numbers, values are best times in milliseconds.
     *
     * @return a map of best completion times.
     */
    public Map<Integer, Double> getLevelCompletionTimes() { 
        return this.levelCompletionTimes; 
    }
    
    /**
     * Gets the map of the maximum gems collected for each level.
     *
     * @return a map where keys are level numbers and values are the maximum gems collected.
     */
    public Map<Integer, Integer> getLevelGemsCollected() { 
        return this.levelGemsCollected; 
    }
}