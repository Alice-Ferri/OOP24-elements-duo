package it.unibo.elementsduo.model.enemies.api;

import it.unibo.elementsduo.model.map.api.Level;

/**
 * Interface representing a projectile in the game.
 */
public interface Projectiles {

    void update(Level level);

    double getX();

    double getY();
    
    double getDirection();

    boolean isActive();

    void move(Level level);
}


