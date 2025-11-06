package it.unibo.elementsduo.model.obstacles.api;

import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.impl.ObstacleType;

/**
 * Factory for creating static obstacles within the game model.
 * This interface defines the method to instantiate different types of
 * obstacles.
 */
@FunctionalInterface
public interface ObstacleFactory {
    /**
     * Creates a new static obstacle with the specified type and HitBox.
     * 
     * @param type   The type of obstacle to create (e.g., GEM, BLOCK).
     * @param hitbox The HitBox that defines the obstacle's position and dimension.
     * @return The newly created StaticObstacle object.
     */
    AbstractStaticObstacle createObstacle(ObstacleType type, HitBox hitbox);
}
