package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents an interactive element that can be pressed and released,
 * such as a button or floor switch.
 */
public interface Pressable extends Collidable {

     /**
      * Activates or "presses" this element.
      */
     void press();

     /**
      * Deactivates or "releases" this element.
      */
     void release();
}
