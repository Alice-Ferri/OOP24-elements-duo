package it.unibo.elementsduo.model.enemies.api;

import java.util.Set;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Represents a projectile, a mobile and short-lived entity that
 * is spawned by an enemy.
 */
public interface Projectiles extends Movable {

    /**
     * Updates the projectile's state, including movement.
     *
     * @param obstacles the set of obstacles in the game, used for any required logic.
     * @param deltaTime the time elapsed since the last frame.
     */
    void update(Set<obstacle> obstacles, double deltaTime);

    /**
     * Executes the base movement calculation.
     *
     * @param obstacles the set of obstacles in the game.
     * @param deltaTime the time elapsed since the last frame.
     */
    void move(Set<obstacle> obstacles, double deltaTime);


    /**
     * Executes physics correction in case of a collision.
     *
     * @param penetration the depth of penetration into the colliding object.
     * @param normal the normal vector of the collision point.
     */
    void correctPhysicsCollision(double penetration, Vector2D normal);

    /**
     * Indicates whether the projectile is still active and should be rendered/updated.
     *
     * @return true if the projectile is active, false otherwise.
     */
    boolean isActive();

    /**
     * Gets the projectile's current X-coordinate.
     *
     * @return the X-coordinate.
     */
    double getX();

    /**
     * Gets the projectile's current Y-coordinate.
     *
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * Gets the direction of the projectile's movement.
     *
     * @return the direction.
     */
    double getDirection();
}