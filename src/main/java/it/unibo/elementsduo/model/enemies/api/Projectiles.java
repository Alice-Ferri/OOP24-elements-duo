package it.unibo.elementsduo.model.enemies.api;

import java.util.Set;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Interface representing a projectile in the game.
 * Projectiles are typically fired by shooter enemies and can interact with the environment.
 */
public interface Projectiles {

    /**
     * @param obstacles the set of obstacles present in the game world.
     * @param deltaTime the time elapsed since the last update.
     */
    void update(Set<obstacle> obstacles, double deltaTime);

    /**
     * @return the X-coordinate.
     */
    double getX();

    /**
     * @return the Y-coordinate.
     */
    double getY();

    /**
     * @return the direction as a double.
     */
    double getDirection();

    /**
     * @return true if the projectile is active, false otherwise.
     */
    boolean isActive();

    /**
     * @param obstacles the set of obstacles to check for collision against.
     * @param deltaTime the time elapsed since the last move calculation.
     */
    void move(Set<obstacle> obstacles, double deltaTime);
}

