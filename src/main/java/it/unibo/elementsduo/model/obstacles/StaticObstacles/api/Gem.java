package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Represents a gem or a static collectible object in the game.
 * Gems are obstacles that can be collected by the player.
 */
public interface Gem extends obstacle {
    /**
     * Checks if the gem is currently active and available for collection.
     * 
     * @return true if the gem has not been collected yet, false otherwise.
     * 
     */
    boolean isActive();

    /**
     * Sets the gem as collected, effectively deactivating it.
     * This method is called when the player interacts with the gem.
     */
    void collect();
}
