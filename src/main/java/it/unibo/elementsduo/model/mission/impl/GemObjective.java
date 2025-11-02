package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;

/**
 * Checks if all gems were collected.
 */
public class GemObjective extends AbstractObjective {

    private final int totalGemsInLevel;

    /**
     * Construct the GemObjective
     * @param totalGems The total number of enemies in the level at the start.
     */
    public GemObjective(final int totalGemsInLevel) {
        super("Collect all " + totalGemsInLevel + " gems");
        this.totalGemsInLevel = totalGemsInLevel;
    }

    @Override
    public void checkCompletion(final GameState finalState, final double finalTimeInSeconds) {
        if (this.isComplete) {
            return;
        }
        if (finalState.getGemsCollected() >= this.totalGemsInLevel) {
            this.isComplete = true;
        }
    }
}