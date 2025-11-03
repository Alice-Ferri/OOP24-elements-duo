package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.GreenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.LavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.WaterPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;

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
    private static final Map<ObstacleType, Function<HitBox, AbstractStaticObstacle>> OBSTACLE_CREATORS = Map.of(
            ObstacleType.WATER_POOL, WaterPool::new,
            ObstacleType.LAVA_POOL, LavaPool::new,
            ObstacleType.GREEN_POOL, GreenPool::new,
            ObstacleType.WALL, Wall::new,
            ObstacleType.FLOOR, Floor::new,
            ObstacleType.WATER_EXIT, WaterExit::new,
            ObstacleType.FIRE_EXIT, FireExit::new);

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
    public AbstractStaticObstacle createObstacle(final ObstacleType type, final HitBox hitbox) {
        return Optional.ofNullable(OBSTACLE_CREATORS.get(type))
                .map(creator -> creator.apply(hitbox))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported obstacle type: " + type));
    }
}
