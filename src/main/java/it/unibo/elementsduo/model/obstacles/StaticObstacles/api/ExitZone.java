package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.obstacles.api.obstacle;

public interface ExitZone extends obstacle {

    void activate();

    boolean isActive();

}
