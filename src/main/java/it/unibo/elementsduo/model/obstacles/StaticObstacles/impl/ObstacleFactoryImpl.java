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
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.FireSpawn;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn.WaterSpawn;

/**
 * Implementation of the {@link ObstacleFactory} interface.
 *
 * <p>
 * This factory is responsible for creating different types of static obstacles
 * based on the provided {@link ObstacleType}. It maps each obstacle type to a
 * corresponding constructor reference and instantiates the appropriate class.
 * </p>
 */
public final class ObstacleFactoryImpl implements ObstacleFactory {

    /** Mapping between obstacle types and their creation functions. */
    private static final Map<ObstacleType.Type, Function<HitBox, AbstractStaticObstacle>> OBSTACLE_CREATORS = Map.of(
            ObstacleType.Type.WATER_POOL, WaterPool::new,
            ObstacleType.Type.LAVA_POOL, LavaPool::new,
            ObstacleType.Type.GREEN_POOL, GreenPool::new,
            ObstacleType.Type.WALL, Wall::new,
            ObstacleType.Type.FLOOR, Floor::new,
            ObstacleType.Type.WATER_SPAWN, WaterSpawn::new,
            ObstacleType.Type.WATER_EXIT, WaterExit::new,
            ObstacleType.Type.FIRE_SPAWN, FireSpawn::new,
            ObstacleType.Type.FIRE_EXIT, FireExit::new);

    /**
     * Creates a new static obstacle of the specified type.
     *
     * @param type   the type of obstacle to create
     * @param hitbox the {@link HitBox} defining the obstacle’s position and
     *               dimensions
     * @return the created {@link AbstractStaticObstacle} instance
     * @throws IllegalArgumentException if the obstacle type is not supported
     */
    @Override
    public AbstractStaticObstacle createObstacle(final ObstacleType.Type type, final HitBox hitbox) {
        return Optional.ofNullable(OBSTACLE_CREATORS.get(type))
                .map(creator -> creator.apply(hitbox))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported obstacle type: " + type));
    }
}
