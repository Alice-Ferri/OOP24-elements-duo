package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;

public class obstacleFactoryImpl implements ObstacleFactory {
    public StaticObstacle createObstacle(final obstacleType.type type, final HitBox hitbox) {
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
            case WATER_SPAWN:
                return new waterSpawn(hitbox);
            case WATER_EXIT:
                return new waterExit(hitbox);
            case FIRE_SPAWN:
                return new fireSpawn(hitbox);
            case FIRE_EXIT:
                return new fireExit(hitbox);

            default:
                throw new IllegalArgumentException("no obstacle");
        }
    }
}