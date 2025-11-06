package it.unibo.elementsduo.model.gameentity.impl;

import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.interactions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.impl.ObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.impl.ObstacleType;
import it.unibo.elementsduo.model.obstacles.staticobstacles.gem.impl.GemImpl;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
import it.unibo.elementsduo.resources.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Concrete implementation of {@link EntityFactory}.
 * Creates game entities using other factories.
 */
public final class EntityFactoryImpl implements EntityFactory {

    private final ObstacleFactory obstacleFactory;
    private final EnemyFactory enemyFactory;
    private final PlayerFactory playerFactory;
    private final InteractiveObstacleFactory interactiveObsFactory;
    private final Map<Character, EntityCreationStrategy> creationMap;

    /**
     * Constructs a new EntityFactory with its required sub-factories.
     */
    public EntityFactoryImpl() {
        this.obstacleFactory = new ObstacleFactoryImpl();
        this.enemyFactory = new EnemyFactoryImpl();
        this.interactiveObsFactory = new InteractiveObstacleFactoryImpl();
        this.creationMap = buildCreationMap();
        this.playerFactory = new PlayerFactoryImpl();
    }

    private Map<Character, EntityCreationStrategy> buildCreationMap() {

        final Map<Character, EntityCreationStrategy> map = new HashMap<>();
        final Function<Position, HitBoxImpl> defaultHitbox = pos -> new HitBoxImpl(pos, 1, 1);

        map.put('P', pos -> this.obstacleFactory.createObstacle(ObstacleType.FLOOR, defaultHitbox.apply(pos)));
        map.put('#', pos -> this.obstacleFactory.createObstacle(ObstacleType.WALL, defaultHitbox.apply(pos)));
        map.put('A', pos -> this.obstacleFactory.createObstacle(ObstacleType.WATER_EXIT, defaultHitbox.apply(pos)));
        map.put('F', pos -> this.obstacleFactory.createObstacle(ObstacleType.FIRE_EXIT, defaultHitbox.apply(pos)));
        map.put('G', GemImpl::new);
        map.put('Q', pos -> this.obstacleFactory.createObstacle(ObstacleType.LAVA_POOL, defaultHitbox.apply(pos)));
        map.put('K', pos -> this.obstacleFactory.createObstacle(ObstacleType.GREEN_POOL, defaultHitbox.apply(pos)));
        map.put('E', pos -> this.obstacleFactory.createObstacle(ObstacleType.WATER_POOL, defaultHitbox.apply(pos)));
        map.put('B', pos -> this.playerFactory.createPlayer(PlayerType.FIREBOY, pos));
        map.put('W', pos -> this.playerFactory.createPlayer(PlayerType.WATERGIRL, pos));
        map.put('C', pos -> this.enemyFactory.createEnemy('C', pos));
        map.put('S', pos -> this.enemyFactory.createEnemy('S', pos));
        map.put('L', this.interactiveObsFactory::createLever);
        map.put('H', this.interactiveObsFactory::createPushBox);
        map.put('R', this.interactiveObsFactory::createButton);
        map.put('M',
                pos -> this.interactiveObsFactory.createMovingPlatform(pos, pos, new Position(pos.x(), pos.y() - 3)));

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
