package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents an obstacle that can be pressed and released.
 * This interface extends {@link Collidable}, meaning that all pressable
 * objects can also participate in collision detection.
 */
public interface Pressable extends Collidable {

    /**
     * Invoked when the object is pressed.
     */
    void press();

    /**
     * Invoked when the object is released.
     */
    void release();
}
