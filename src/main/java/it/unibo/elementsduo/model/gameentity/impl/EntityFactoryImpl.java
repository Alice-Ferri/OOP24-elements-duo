package it.unibo.elementsduo.model.gameentity.impl;

import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
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

    private interface EntityCreationStrategy {
        GameEntity create(Position pos);
    }

    private final ObstacleFactory obstacleFactory;
    private final EnemyFactory enemyFactory;
    private final InteractiveObstacleFactory interactiveObsFactory;
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
                             final InteractiveObstacleFactory interactiveObsFactory) {
        this.obstacleFactory = Objects.requireNonNull(obstacleFactory);
        this.enemyFactory = Objects.requireNonNull(enemyFactory);
        this.interactiveObsFactory = Objects.requireNonNull(interactiveObsFactory);
        
        this.creationMap = buildCreationMap();
    }

    private Map<Character, EntityCreationStrategy> buildCreationMap() {

        final Map<Character, EntityCreationStrategy> map = new HashMap<>();
        final Function<Position, HitBoxImpl> defaultHitbox = 
            pos -> new HitBoxImpl(pos, 1, 1);

        map.put('P', pos -> this.obstacleFactory.createObstacle(obstacleType.type.FLOOR, defaultHitbox.apply(pos)));
        map.put('#', pos -> this.obstacleFactory.createObstacle(obstacleType.type.WALL, defaultHitbox.apply(pos)));
        map.put('A', pos -> this.obstacleFactory.createObstacle(obstacleType.type.WATER_EXIT, defaultHitbox.apply(pos)));
        map.put('F', pos -> this.obstacleFactory.createObstacle(obstacleType.type.FIRE_EXIT, defaultHitbox.apply(pos)));
        //map.put('G', pos -> this.obstacleFactory.createObstacle(obstacleType.type.GEM, defaultHitbox.apply(pos)));
        map.put('Q', pos -> this.obstacleFactory.createObstacle(obstacleType.type.LAVA_POOL, defaultHitbox.apply(pos)));
        map.put('K', pos -> this.obstacleFactory.createObstacle(obstacleType.type.GREEN_POOL, defaultHitbox.apply(pos)));
        map.put('E', pos -> this.obstacleFactory.createObstacle(obstacleType.type.WATER_POOL, defaultHitbox.apply(pos)));
        map.put('B', Fireboy::new);
        map.put('W', Watergirl::new);
        map.put('C', pos -> this.enemyFactory.createEnemy('C', pos));
        map.put('S', pos -> this.enemyFactory.createEnemy('S', pos));
        map.put('L', this.interactiveObsFactory::createLever);
        map.put('H', this.interactiveObsFactory::createPushBox);
        map.put('R', this.interactiveObsFactory::createButton);
        map.put('M', pos -> this.interactiveObsFactory.createMovingPlatform(pos, pos, new Position(pos.x(), pos.y() - 3)));

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
}