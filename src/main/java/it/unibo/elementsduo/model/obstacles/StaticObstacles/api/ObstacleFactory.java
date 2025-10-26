package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleType;

public interface ObstacleFactory {
    StaticObstacle createObstacle(final obstacleType.type type, final HitBox hitbox);
}
