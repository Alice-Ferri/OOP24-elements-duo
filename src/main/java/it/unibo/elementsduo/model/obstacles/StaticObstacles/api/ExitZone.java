package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Represents an exit area in the game that players can activate to complete a
 * level.
 * 
 * <p>
 * Implementations define how the exit zone becomes active and how it interacts
 * with players.
 */
public interface ExitZone extends obstacle {

    /**
     * Activates the exit zone.
     * 
     * <p>
     * Once activated, it allows the corresponding player to finish the level.
     */
    void activate();

    /**
     * Checks whether the exit zone is currently active.
     * 
     * <p>
     *
     * @return {@code true} if the exit is active, {@code false} otherwise
     */
    boolean isActive();
}
