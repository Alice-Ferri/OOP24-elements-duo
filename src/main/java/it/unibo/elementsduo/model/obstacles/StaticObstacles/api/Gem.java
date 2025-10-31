package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

public interface Gem extends obstacle {
    boolean isActive();

    void collect();
}
