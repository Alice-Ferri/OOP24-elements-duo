package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;

import it.unibo.elementsduo.controller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.events.api.EventListener;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;

/**
 * Represents a generic enemy in the game.
 */
public interface Enemy extends Movable, ManagerInjectable, Collidable, EventListener, GameEntity {

    /**
     * @return an {@link Optional} containing a {@link Projectiles} instance if an
     *         attack occurs,
     *         or an empty {@link Optional} otherwise.
     */
    Optional<Projectiles> attack();

    /**
     * Updates the enemy's state, including movement and behavioral logic.
     *
     * @param obstacles the set of obstacles currently present in the game world.
     * @param deltaTime the time elapsed since the last update.
     */
    void update(double deltaTime);

    /**
     * * @return true if the enemy is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Reverses the enemy's current movement direction.
     */
    void setDirection();

    /**
     * @return the X-coordinate.
     */
    double getX();

    /**
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * @return the current movement direction (+1 or -1).
     */
    double getDirection();

    HitBox getHitBox();

    void setMoveManager(EnemiesMoveManager manager);

    void die();

}