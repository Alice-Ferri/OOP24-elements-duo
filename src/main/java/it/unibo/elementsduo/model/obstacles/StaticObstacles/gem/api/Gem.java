package it.unibo.elementsduo.model.obstacles.StaticObstacles.gem.api;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Represents a gem or a static collectible object in the game.
 * 
 * <p>
 * Gems are obstacles that can be collected by the player.
 */
public interface Gem extends obstacle {

    /**
     * Checks if the gem is currently active and available for collection.
     * 
     * <p>
     *
     * @return {@code true} if the gem has not been collected yet, {@code false}
     *         otherwise
     */
    boolean isActive();

    /**
     * Sets the gem as collected, effectively deactivating it.
     * 
     * <p>
     * This method is called when the player interacts with the gem.
     */
    void collect();
}
