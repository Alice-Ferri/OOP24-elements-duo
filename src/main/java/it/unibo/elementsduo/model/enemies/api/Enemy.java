package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;

import it.unibo.elementsduo.model.map.api.Level;

public interface Enemy {
    Optional<Projectiles> attack();

    void update(Level level);
    
    boolean isAlive();

    void setDirection();

    void move(Level level);

    double getX();

    double getY();

    double getDirection();

    EnemiesType getType();

}

