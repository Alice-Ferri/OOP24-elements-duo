package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;

public class obstacleFactoryImpl implements ObstacleFactory {
    private static final Map<obstacleType.type, Function<HitBox, StaticObstacle>> OBSTACLE_CREATORS = Map.of(
            obstacleType.type.WATER_POOL, waterPool::new,
            obstacleType.type.LAVA_POOL, lavaPool::new,
            obstacleType.type.GREEN_POOL, greenPool::new,
            obstacleType.type.WALL, Wall::new,
            obstacleType.type.FLOOR, Floor::new,
            obstacleType.type.WATER_SPAWN, waterSpawn::new,
            obstacleType.type.WATER_EXIT, WaterExit::new,
            obstacleType.type.FIRE_SPAWN, fireSpawn::new,
            obstacleType.type.FIRE_EXIT, FireExit::new);

    public StaticObstacle createObstacle(final obstacleType.type type, final HitBox hitbox) {
        return Optional.ofNullable(OBSTACLE_CREATORS.get(type))
                .map(creator -> creator.apply(hitbox))
                .orElseThrow(() -> new IllegalArgumentException("no obstacle"));
    }
}