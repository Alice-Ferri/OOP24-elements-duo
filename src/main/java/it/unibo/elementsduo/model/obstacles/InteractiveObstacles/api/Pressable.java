package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
<<<<<<< HEAD
 * Represents an interactive element that can be pressed and released,
 * such as a button or floor switch.
=======
 * Represents an obstacle that can be pressed and released.
 * This interface extends {@link Collidable}, meaning that all pressable
 * objects can also participate in collision detection.
>>>>>>> powerups
 */
public interface Pressable extends Collidable {

    /**
<<<<<<< HEAD
     * Activates or "presses" this element.
=======
     * Invoked when the object is pressed.
>>>>>>> powerups
     */
    void press();

    /**
<<<<<<< HEAD
     * Deactivates or "releases" this element.
=======
     * Invoked when the object is released.
>>>>>>> powerups
     */
    void release();
}
