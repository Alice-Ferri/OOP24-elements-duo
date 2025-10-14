package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;
import java.util.Set;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

public interface Enemy {
    Optional<Projectiles> attack();

    void update(Set<obstacle> obstacles, double deltaTime);
    
    boolean isAlive();

    void setDirection();

    void move(Set<obstacle> obstacles, double deltaTime);

    double getX();

    double getY();

    double getDirection();

    EnemiesType getType();

}

