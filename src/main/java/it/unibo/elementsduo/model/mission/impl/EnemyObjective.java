package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;

/**
 * Checks if all enemies were defeated.
 */
public final class EnemyObjective extends AbstractObjective {

    private final int totalEnemiesInLevel;

    /**
     * Constructs the EnemyObjective.
     *
     * @param totalEnemiesInLevel The total number of enemies in the level at the start.
     */
    public EnemyObjective(final int totalEnemiesInLevel) {
        super("Defeat all " + totalEnemiesInLevel + " enemies");
        this.totalEnemiesInLevel = totalEnemiesInLevel;
    }

    @Override
    protected boolean checkObjectiveLogic(final GameState finalState, final double finalTimeInSeconds) {
        return finalState.getEnemiesDefeated() == totalEnemiesInLevel;
    }

}
