package it.unibo.elementsduo.model.interactions.core.api;

import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents information about a detected collision between two
 * {@link Collidable} objects.
 * 
 * <p>
 * Provides details such as the colliding objects, the penetration depth, and
 * the collision normal.
 */
public interface CollisionInformations {

    /**
     * Returns the first collidable object involved in the collision.
     *
     * @return the first {@link Collidable} object
     */
    Collidable getObjectA();

    /**
     * Returns the second collidable object involved in the collision.
     *
     * @return the second {@link Collidable} object
     */
    Collidable getObjectB();

    /**
     * Returns the penetration depth of the collision.
     * 
     * <p>
     * The penetration value represents how much the two objects overlap.
     *
     * @return the collision penetration depth
     */
    double getPenetration();

    /**
     * Returns the collision normal vector.
     * 
     * <p>
     * The normal indicates the direction from object A to object B in which the
     * collision occurred.
     *
     * @return the collision normal as a {@link Vector2D}
     */
    Vector2D getNormal();
}
