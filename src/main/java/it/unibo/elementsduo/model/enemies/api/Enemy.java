package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;

import it.unibo.elementsduo.model.gameentity.api.Updatable;
import it.unibo.elementsduo.model.interactions.core.api.Movable;

/**
 * Represents a generic enemy in the game.
 */
public interface Enemy extends Movable, ManagerInjectable, Updatable {

    /**
     * Attempts to perform an attack action based on internal conditions (e.g.,
     * cooldown).
     *
     * @return an {@link Optional} containing a new {@link Projectiles} instance if
     *         the attack is ok,else {@link Optional#empty}.
     */

    Optional<Projectiles> attack();

    /**
     * Checks if the enemy is currently alive.
     *
     * @return true if the enemy is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Reverses the enemy's current movement direction.
     */
    void setDirection();

    /**
     * Gets the X-coordinate of the enemy's position.
     *
     * @return the X-coordinate.
     */
    double getX();

    /**
     * Gets the Y-coordinate of the enemy's position.
     *
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * Gets the current movement direction.
     *
     * @return the current movement direction.
     */
    int getDirection();

    /**
     * Sets the enemy's state to dead.
     */
    void die();
}
