package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;

/**
 * Represents a generic enemy in the game.
 */
public interface Enemy extends Movable, ManagerInjectable, Collidable, GameEntity {

    /**
     * Attempts to perform an attack action.
     *
     * @return an {@link Optional} containing a {@link Projectiles} instance if an
     * *attack occurs,
     * *or an empty {@link Optional} otherwise.
     */
    Optional<Projectiles> attack();

    /**
     * Updates the enemy's state, including movement and behavioral logic.
     *
     * @param deltaTime the time elapsed since the last update.
     */
    void update(double deltaTime);

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
    double getDirection();

    /**
     * Sets the enemy's state to dead.
     */
    void die(); 
}

