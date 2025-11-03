package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents an obstacle that can switch between two states when toggled.
 * This interface extends {@link Collidable}, allowing it to interact within
 * the collision system.
 */
public interface Toggler extends Collidable {

    /**
     * Toggles the state of the object.
     */
    void toggle();
}
