package it.unibo.elementsduo.model.mission.impl;

import it.unibo.elementsduo.model.gamestate.api.GameState;
import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.mission.api.Objective;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem; 
import it.unibo.elementsduo.model.enemies.api.Enemy;

/**
 * Manages the root mission for the level.
 * It is initialized at the start of the match with the Level
 * and calculates the results at the end with GameState and time.
 */
public final class MissionManager {
    private static final double TIME_LIMIT = 60.0;
    private final Objective rootObjective;

    /**
     * Constructs the MissionManager with the initial state of the level
     * to get the total counts for gems and enemies.
     *
     * @param initialLevel The Level at the start of the match.
     */
    public MissionManager(final Level initialLevel) {
        final int totalGems = initialLevel.getEntitiesByClass(Gem.class).size();
        final int totalEnemies = initialLevel.getEntitiesByClass(Enemy.class).size();

        final Mission allTasks = new Mission("Complete all the objectives!");
        allTasks.add(new GemObjective(totalGems));
        allTasks.add(new EnemyObjective(totalEnemies));
        allTasks.add(new TimeLimitObjective(TIME_LIMIT));

        this.rootObjective = allTasks;
    }

    /**
     * Method called at the end of the match.
     * It starts the checkCompletion process on the Mission.
     *
     * @param finalState         The final state of the game.
     * @param finalTimeInSeconds The total time elapsed.
     */
    public void calculateFinalScore(final GameState finalState, final double finalTimeInSeconds) {
        this.rootObjective.checkCompletion(finalState, finalTimeInSeconds);
    }

    /**
     * Return if the mission is completed.
     *
     * @return true if the root mission (all objectives) is complete.
     */
    public boolean areAllObjectivesComplete() {
        return this.rootObjective.isComplete();
    }

    /**
     * Return the root mission.
     *
     * @return The mission.
     */
    public Objective getRootObjective() {
        return this.rootObjective;
    }
}
