package it.unibo.elementsduo.model.enemies.api;

import java.util.Optional;

import it.unibo.elementsduo.resources.Position;

public interface Enemy {
    void move();

    Optional<Projectiles> attack();

    
    boolean isAlive();

    Position GetPosition();
}

