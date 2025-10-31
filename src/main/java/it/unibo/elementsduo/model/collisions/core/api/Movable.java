package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents a collidable object capable of moving and reacting to
 * physics-based collisions.
 * 
 * <p>
 * Implementations of this interface handle position correction after a
 * collision occurs
 * to prevent overlapping and maintain realistic physical behavior.
 */
public interface Movable {

    /**
     * Corrects the position and velocity of the movable object after a collision.
     *
     * @param penetration the depth of penetration between the colliding objects
     * @param normal      the collision normal indicating the direction of
     *                    correction
     */
    void correctPhysicsCollision(double penetration, Vector2D normal);
}
