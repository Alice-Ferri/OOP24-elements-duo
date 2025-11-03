package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
<<<<<<< HEAD
 * Represents an interactive element that can switch between two states,
 * such as an on/off lever or switch.
=======
 * Represents an obstacle that can switch between two states when toggled.
 * This interface extends {@link Collidable}, allowing it to interact within
 * the collision system.
>>>>>>> powerups
 */
public interface Toggler extends Collidable {

    /**
<<<<<<< HEAD
     * Toggles the state of this element between active and inactive.
=======
     * Toggles the state of the object.
>>>>>>> powerups
     */
    void toggle();
}
