package it.unibo.elementsduo.model.gameentity.impl;

import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.ObstacleType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.model.powerups.api.PowerUpFactory;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Concrete implementation of {@link EntityFactory}.
 * Creates game entities using other factories.
 */
public final class EntityFactoryImpl implements EntityFactory {

    private final ObstacleFactory obstacleFactory;
    private final EnemyFactory enemyFactory;
    private final InteractiveObstacleFactory interactiveObsFactory;
    private final PowerUpFactory powerUpFactory;
    private final Map<Character, EntityCreationStrategy> creationMap;

    /**
     * Constructs a new EntityFactory with its required sub-factories.
     *
     * @param obstacleFactory       Factory for creating static obstacles.
     * @param enemyFactory          Factory for creating enemies.
     * @param interactiveObsFactory Factory for creating interactive obstacles.
     */
    public EntityFactoryImpl(final ObstacleFactory obstacleFactory,
            final EnemyFactory enemyFactory,
            final InteractiveObstacleFactory interactiveObsFactory, final PowerUpFactory powerUpFactory) {
        this.obstacleFactory = Objects.requireNonNull(obstacleFactory);
        this.enemyFactory = Objects.requireNonNull(enemyFactory);
        this.interactiveObsFactory = Objects.requireNonNull(interactiveObsFactory);
        this.creationMap = buildCreationMap();
        this.powerUpFactory = powerUpFactory;
    }

    private Map<Character, EntityCreationStrategy> buildCreationMap() {

        final Map<Character, EntityCreationStrategy> map = new HashMap<>();
        final Function<Position, HitBoxImpl> defaultHitbox = pos -> new HitBoxImpl(pos, 1, 1);

        map.put('P', pos -> this.obstacleFactory.createObstacle(ObstacleType.FLOOR, defaultHitbox.apply(pos)));
        map.put('#', pos -> this.obstacleFactory.createObstacle(ObstacleType.WALL, defaultHitbox.apply(pos)));
        map.put('A', pos -> this.obstacleFactory.createObstacle(ObstacleType.WATER_EXIT, defaultHitbox.apply(pos)));
        map.put('F', pos -> this.obstacleFactory.createObstacle(ObstacleType.FIRE_EXIT, defaultHitbox.apply(pos)));
        // map.put('G', pos -> this.obstacleFactory.createObstacle(ObstacleType.GEM,
        // defaultHitbox.apply(pos)));
        map.put('Q', pos -> this.obstacleFactory.createObstacle(ObstacleType.LAVA_POOL, defaultHitbox.apply(pos)));
        map.put('K', pos -> this.obstacleFactory.createObstacle(ObstacleType.GREEN_POOL, defaultHitbox.apply(pos)));
        map.put('E', pos -> this.obstacleFactory.createObstacle(ObstacleType.WATER_POOL, defaultHitbox.apply(pos)));
        map.put('B', Fireboy::new);
        map.put('W', Watergirl::new);
        map.put('C', pos -> this.enemyFactory.createEnemy('C', pos));
        map.put('S', pos -> this.enemyFactory.createEnemy('S', pos));
        map.put('L', this.interactiveObsFactory::createLever);
        map.put('H', this.interactiveObsFactory::createPushBox);
        map.put('R', this.interactiveObsFactory::createButton);
        map.put('M',
                pos -> this.interactiveObsFactory.createMovingPlatform(pos, pos, new Position(pos.x(), pos.y() - 3)));
        map.put('I', pos -> this.powerUpFactory.createPowerUp(PowerUpType.HAZARD_IMMUNITY, pos));
        map.put('N', pos -> this.powerUpFactory.createPowerUp(PowerUpType.ENEMY_KILL, pos));

        return Collections.unmodifiableMap(map);
    }

    @Override
    public GameEntity createEntity(final char symbol, final Position pos) {
        final EntityCreationStrategy strategy = this.creationMap.get(symbol);
        if (strategy == null) {
            return null;
        }
        return strategy.create(pos);
    }

    @FunctionalInterface
    private interface EntityCreationStrategy {
        GameEntity create(Position pos);
    }
}
