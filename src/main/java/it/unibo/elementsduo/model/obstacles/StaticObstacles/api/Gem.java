package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

public interface Gem extends Collidable {
    boolean isActive();

    void collect();
}
