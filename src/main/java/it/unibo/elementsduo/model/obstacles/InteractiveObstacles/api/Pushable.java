package it.unibo.elementsduo.model.obstacles.interactiveobstacles.api;

import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents an object that can be physically interacted with through
 * pushing or movement.
 * 
 * <p>
 * Classes implementing this interface define how an object reacts when a
 * push force is applied or when it is moved directly in the game world.
 */
public interface Pushable {

    /**
     * Applies a push force to this object.
     * 
     *
     * @param v the force vector applied to the object
     */
    void push(Vector2D v);

    /**
     * Moves this object by the specified delta.
     * 
     *
     * @param delta the movement vector representing the position change
     */
    void move(Vector2D delta);
}
