package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

/**
 * Represents a generic enemy in the game.
 * An enemy is a dynamic entity that can move, attack, and interact with the environment.
 */
public interface Enemy {

    /**
     * @return an {@link Optional} containing a {@link Projectiles} instance if an attack occurs, 
     * or an empty {@link Optional} otherwise.
     */
    Optional<Projectiles> attack();

    /**
     * @param obstacles the set of obstacles currently present in the game world.
     * @param deltaTime the time elapsed since the last update (in milliseconds or game ticks).
     */
    void update(Set<Obstacle> obstacles, double deltaTime);

    /** 
     * @return true if the enemy is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Reverses the enemy's current movement direction.
     */
    void setDirection();

    /**
     * @param obstacles the set of obstacles to check for collision against.
     * @param deltaTime the time elapsed since the last move calculation.
     */
    void move(Set<Obstacle> obstacles, double deltaTime);

    /**
     * @return the X-coordinate.
     */
    double getX();

    /**
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * @return the direction.
     */
    double getDirection();
}
