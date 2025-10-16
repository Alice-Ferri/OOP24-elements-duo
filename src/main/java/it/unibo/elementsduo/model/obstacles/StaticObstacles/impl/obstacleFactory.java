package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public class obstacleFactory {
    public obstacle createObstacle(final obstacleType.type type, final HitBox hitbox) {
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