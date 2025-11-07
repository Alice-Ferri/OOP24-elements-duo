package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.interactions.core.api.Movable;

/**
 * Represents a projectile, a mobile and short-lived entity that
 * is spawned by an enemy.
 */

public interface Projectiles extends Movable, Updatable {

    /**
     * Indicates whether the projectile is still active and should be
     * rendered/updated.
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

    /**
     * Sets the projectile to an inactive state, removing it from the game world.
     */
    void deactivate();
}
