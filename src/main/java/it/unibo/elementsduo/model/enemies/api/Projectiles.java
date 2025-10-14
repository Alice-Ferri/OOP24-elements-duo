package it.unibo.elementsduo.model.enemies.api;

import java.util.Set;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Interface representing a projectile in the game.
 */
public interface Projectiles {

    void update(Set<obstacle> obstacles, double deltaTime);

    double getX();

    double getY();
    
    double getDirection();

    boolean isActive();

    void move(Set<obstacle> obstacles, double deltaTime);
}


