package it.unibo.elementsduo.model.enemies.api;

/**
 * Interface representing a projectile in the game.
 */
public interface Projectiles {

    /**
     * Moves the projectile according to its direction and speed.
     */
    void update();

    /**
     * Returns the current position of the projectile.
     * @return the projectile's position
     */
    double getX();
    double getY();
    double getDirection();

    /**
     * Checks if the projectile is still active.
     * (e.g., it has not left the screen and has not hit a target).
     * @return true if the projectile is active, false otherwise
     */
    boolean isActive();

    /**
     * Deactivates the projectile (when it hits a target or leaves the game area).
     */

}


