package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.WaterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.fireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.waterSpawn;

public class ObstacleFactoryImpl implements ObstacleFactory {
    private static final Map<ObstacleType.type, Function<HitBox, AbstractStaticObstacle>> OBSTACLE_CREATORS = Map.of(
            ObstacleType.type.WATER_POOL, WaterPool::new,
            ObstacleType.type.LAVA_POOL, LavaPool::new,
            ObstacleType.type.GREEN_POOL, GreenPool::new,
            ObstacleType.type.WALL, Wall::new,
            ObstacleType.type.FLOOR, Floor::new,
            ObstacleType.type.WATER_SPAWN, waterSpawn::new,
            ObstacleType.type.WATER_EXIT, WaterExit::new,
            ObstacleType.type.FIRE_SPAWN, fireSpawn::new,
            ObstacleType.type.FIRE_EXIT, FireExit::new);

    public AbstractStaticObstacle createObstacle(final ObstacleType.type type, final HitBox hitbox) {
        return Optional.ofNullable(OBSTACLE_CREATORS.get(type))
                .map(creator -> creator.apply(hitbox))
                .orElseThrow(() -> new IllegalArgumentException("no obstacle"));
    }
}