package it.unibo.elementsduo.model.gameentity.impl;

import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.EnemyFactory;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.EntityType;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.InteractiveObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EntityFactoryImpl implements EntityFactory{

    private final ObstacleFactory obstacleFactory;
    private final EnemyFactory enemyFactory;
    private final InteractiveObstacleFactory interactiveObsFactory;

    private static final Map<Character, EntityType> SYMBOL_REGISTRY = buildSymbolRegistry();
    private static final Map<Character, obstacleType.type> STATIC_TYPE_MAP = Map.of(
            'P', obstacleType.type.FLOOR,
            '#', obstacleType.type.WALL,
            'A', obstacleType.type.WATER_EXIT,
            'F', obstacleType.type.FIRE_EXIT,
            'B', obstacleType.type.FIRE_SPAWN,
            'W', obstacleType.type.WATER_SPAWN);

    private static Map<Character, EntityType> buildSymbolRegistry() {
        Map<Character, EntityType> map = new HashMap<>();

        map.put('P', EntityType.STATIC_OBSTACLE);
        map.put('#', EntityType.STATIC_OBSTACLE);
        map.put('A', EntityType.STATIC_OBSTACLE);
        map.put('F', EntityType.STATIC_OBSTACLE);
        map.put('B', EntityType.SPAWN_POINT);
        map.put('W', EntityType.SPAWN_POINT);
        map.put('C', EntityType.ENEMY);
        map.put('S', EntityType.ENEMY);
        map.put('L', EntityType.LEVER);
        map.put('H', EntityType.PUSH_BOX);
        map.put('M', EntityType.MOVING_PLATFORM);

        return Collections.unmodifiableMap(map);
    }

    public EntityFactoryImpl(final ObstacleFactory obstacleFactory,final EnemyFactory enemyFactory,final InteractiveObstacleFactory interactiveObsFactory) 
    {
        this.obstacleFactory = Objects.requireNonNull(obstacleFactory);
        this.enemyFactory = Objects.requireNonNull(enemyFactory);
        this.interactiveObsFactory = Objects.requireNonNull(interactiveObsFactory);
    }

    public Set<GameEntity> createEntities(final char symbol, final Position pos) {
        final EntityType type = SYMBOL_REGISTRY.get(symbol);

        if (type == null) {
            return Collections.emptySet();
        }

        final HitBoxImpl defaultHitbox = new HitBoxImpl(pos, 1, 1);
        final Set<GameEntity> created = new HashSet<>(); 

        switch (type) {
            case STATIC_OBSTACLE:
                created.add(obstacleFactory.createObstacle(STATIC_TYPE_MAP.get(symbol), defaultHitbox));
                break;
            case ENEMY:
                created.add(enemyFactory.createEnemy(symbol, pos));
                break;
            case SPAWN_POINT: 
                created.add((symbol == 'B') ? new Watergirl(pos) : new Fireboy(pos));
                break;
            case LEVER:
                created.add(interactiveObsFactory.createLever(pos));
                break;
            case PUSH_BOX:
                created.add(interactiveObsFactory.createPushBox(pos));
                break;
            case MOVING_PLATFORM:
                created.add(interactiveObsFactory.createMovingPlatform(pos, pos, new Position(pos.x() - 3, pos.y() - 3)));
                break;
            default:
                System.err.println("Warning: Unhandled EntityType in factory for symbol '" + symbol + "'");
                break;
        }
        return created; 
    }
}
