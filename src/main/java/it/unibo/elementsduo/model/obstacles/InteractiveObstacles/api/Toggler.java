package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents an interactive element that can switch between two states,
 * such as an on/off lever or switch.
 */
public interface Toggler extends Collidable {

    /**
     * Toggles the state of this element between active and inactive.
     */
    void toggle();
}
