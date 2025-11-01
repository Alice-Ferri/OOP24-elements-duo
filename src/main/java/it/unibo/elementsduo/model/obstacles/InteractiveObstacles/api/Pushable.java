package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

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
     * <p>
     * The push may affect the object's velocity, acceleration, or position
     * depending on the specific implementation.
     *
     * @param v the force vector applied to the object
     */
    void push(Vector2D v);

    /**
     * Moves this object by the specified delta.
     * 
     * <p>
     * The movement is typically used to update the object's position
     * directly, bypassing physics calculations.
     *
     * @param delta the movement vector representing the position change
     */
    void move(Vector2D delta);
}
