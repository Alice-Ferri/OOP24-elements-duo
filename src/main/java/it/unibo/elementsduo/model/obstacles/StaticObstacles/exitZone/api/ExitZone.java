package it.unibo.elementsduo.model.obstacles.staticObstacles.exitZone.api;

import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.staticObstacles.exitZone.impl.ExitType;

/**
 * Represents an exit area in the game that players can activate to complete a
 * level.
 * 
 * <p>
 * Implementations define how the exit zone becomes active and how it interacts
 * with players.
 */
public interface ExitZone extends Obstacle {

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
     * @return {@code true} if the exit has been activated, {@code false} otherwise
     */
    boolean isActive();

    /**
     * @return the type of exit zone (fireExit or waterExit)
     */
    ExitType getExitType();
}
