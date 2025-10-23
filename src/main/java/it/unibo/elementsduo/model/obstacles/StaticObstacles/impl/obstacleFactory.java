package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;

public class obstacleFactory {
    public Obstacle createObstacle(final obstacleType.type type, final HitBox hitbox) {
        switch (type) {
            case WATER_POOL:
                return new waterPool(hitbox);
            case LAVA_POOL:
                return new lavaPool(hitbox);
            case GREEN_POOL:
                return new greenPool(hitbox);
            case WALL:
                return new Wall(hitbox);
            case FLOOR:
                return new Floor(hitbox);

            default:
                throw new IllegalArgumentException("no obstacle");
        }
    }
}