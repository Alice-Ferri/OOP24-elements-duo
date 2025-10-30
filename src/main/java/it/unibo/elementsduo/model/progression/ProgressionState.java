package it.unibo.elementsduo.model.progression;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player's progression state in the game, including unlocked levels,
 * collected gems, and completion times.
 */
public final class ProgressionState {

    private int currentLevel; 
    private int collectedGems; 
    private final Map<Integer, Long> levelCompletionTimes = new HashMap<>(); 
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
     * @param collectedGems the initial count of collected gems.
     */
    public ProgressionState(final int currentLevel, final int collectedGems) {
        this.currentLevel = currentLevel;
        this.collectedGems = collectedGems;
    }

    /**
     * Adds a completion time for a given level, only updating it if the new time is a record (lower).
     *
     * @param levelNumber the number of the level completed.
     * @param timeMillis the time taken to complete the level, in milliseconds.
     */
    public void addLevelCompletionTime(final int levelNumber, final long timeMillis, final int gemsCollected) {

        if (this.levelCompletionTimes.containsKey(levelNumber)) {

        if (!this.levelCompletionTimes.containsKey(levelNumber) || timeMillis < this.levelCompletionTimes.get(levelNumber)) {
            this.levelCompletionTimes.put(levelNumber, timeMillis);
        }
        
        if (!this.levelGemsCollected.containsKey(levelNumber) || gemsCollected > this.levelGemsCollected.get(levelNumber)) {
            this.levelGemsCollected.put(levelNumber, gemsCollected);
        }
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
     * Gets the total number of gems collected across all played levels.
     *
     * @return the total collected gems.
     */
    public int getCollectedGems() { 
        return this.collectedGems; 
    }

    /**
     * Sets the total number of gems collected.
     *
     * @param collectedGems the new total collected gems.
     */
    public void setCollectedGems(final int collectedGems) { 
        this.collectedGems = collectedGems; 
    }

    /**
     * Gets the map of completion times. Keys are level numbers, values are best times in milliseconds.
     *
     * @return an unmodifiable map of best completion times.
     */
    public Map<Integer, Long> getLevelCompletionTimes() { 
        return this.levelCompletionTimes; 
    }
}
