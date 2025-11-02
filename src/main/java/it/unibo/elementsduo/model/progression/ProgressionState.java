package it.unibo.elementsduo.model.progression;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player's progression state in the game, including unlocked levels,
 * best times, and maximum gems collected per level.
 */
public final class ProgressionState {

    private int currentLevel; 
    private final Map<Integer, Double> levelCompletionTimes = new HashMap<>(); 
    private final Map<Integer, String> levelMissionCompleted = new HashMap<>();

    /**
     * Default constructor.
     * Creates a new, empty progression state.
     */
    public ProgressionState() {
        // Empty constructor, maps are already initialized by field declaration.
    }

    /**
     * Copy constructor.
     * Creates a new instance of ProgressionState by copying data from another.
     * used for defensive copies.
     *
     * @param other the state to copy data from.
     */
    public ProgressionState(final ProgressionState other) {
        this.currentLevel = other.currentLevel;
        this.levelCompletionTimes.putAll(other.levelCompletionTimes);
        this.levelMissionCompleted.putAll(other.levelMissionCompleted);
    }

    /**
     * Updates the progression data for a given level.
     * Saves the new time if it is a record (lower time).
     * Saves if the mission is completed or not.
     *
     * @param levelNumber the number of the level completed.
     * @param timeSeconds the time taken to complete the level, in milliseconds.
     * @param missionCompleted return true if the mission is completed.
     */
    public void addLevelCompletionTime(final int levelNumber, final double timeSeconds, final boolean missionCompleted) {

        final boolean isNewBestTime = !this.levelCompletionTimes.containsKey(levelNumber) 
        || timeSeconds < this.levelCompletionTimes.get(levelNumber);

        if (isNewBestTime) {
            this.levelCompletionTimes.put(levelNumber, timeSeconds);
        }

        if (missionCompleted) {
            this.levelMissionCompleted.put(levelNumber, "* Sfida Completata!");
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
        return Collections.unmodifiableMap(this.levelCompletionTimes); 
    }

    /**
     * Gets the map of the maximum gems collected for each level.
     *
     * @return a map where keys are level numbers and values are the maximum gems collected.
     */
    public Map<Integer, String> getLevelMissionCompleted() { 
        return Collections.unmodifiableMap(this.levelMissionCompleted); 
    }
}
