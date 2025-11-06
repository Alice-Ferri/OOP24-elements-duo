package it.unibo.elementsduo.model.obstacles.interactiveobstacles.api;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;

/**
 * Represents an interactive element that can be pressed and released,
 * such as a button
 */
public interface Pressable extends Collidable {

     /**
      * press this element.
      */
     void press();

     /**
      * release this element.
      */
     void release();
}
