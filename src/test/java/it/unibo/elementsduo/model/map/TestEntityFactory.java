package it.unibo.elementsduo.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.ObstacleFactoryImpl;
import it.unibo.elementsduo.model.powerups.impl.PowerUpFactoryImpl;
import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.impl.EntityFactoryImpl;
import it.unibo.elementsduo.resources.Position;
import java.util.Set;

/**
 * Integration test for the {@link EntityFactoryImpl} class.
 * It verifies that all valid symbols create entities and invalid symbols return null.
 */
final class TestEntityFactory {

    private EntityFactory entityFactory;

    /**
     * Initializes the EntityFactory by injecting the implementations
     * of others factories before each test.
     */
    @BeforeEach
    void setUp() {
        final ObstacleFactoryImpl obstacleFactory = new ObstacleFactoryImpl();
        final EnemyFactoryImpl enemyFactory = new EnemyFactoryImpl();
        final InteractiveObstacleFactoryImpl interactiveObsFactory = new InteractiveObstacleFactoryImpl();
        final PowerUpFactoryImpl powerUpFactoryImpl = new PowerUpFactoryImpl();

        this.entityFactory = new EntityFactoryImpl(
            obstacleFactory, 
            enemyFactory, 
            interactiveObsFactory,
            powerUpFactoryImpl
            
        );
    }

    /**
     * Tests that the constructor throws a NullPointerException
     * if any factory dependency is null.
     */
    @Test
    void testConstructorNullChecks() {
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(null, new EnemyFactoryImpl(), new InteractiveObstacleFactoryImpl(), new PowerUpFactoryImpl()));
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(new ObstacleFactoryImpl(), null, new InteractiveObstacleFactoryImpl(), new PowerUpFactoryImpl()));
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(new ObstacleFactoryImpl(), new EnemyFactoryImpl(), null, new PowerUpFactoryImpl()));
    }

    /**
     * Tests that every single valid symbol
     * produces a non-null entity.
     */
    @Test
    void testAllValidSymbolsAreCreated() {
        final Set<Character> validSymbols = Set.of(
            'P', '#', 'A', 'F', 'Q', 'E', 'K',
            'B', 'W', 'C', 'S', 'L', 'H', 'M', 'R'
        );

        final Position pos = new Position(1, 1);

        for (final char symbol : validSymbols) {
            final GameEntity result = entityFactory.createEntity(symbol, pos);

            assertNotNull(result, 
                "The entity created for the symbol '" + symbol + "' was null.");
        }
    }

    /**
     * Tests that a set of invalid symbols
     * returns null.
     */
    @Test
    void testInvalidSymbolsReturnNull() {
        final Set<Character> invalidSymbols = Set.of('X', 'Z', '?', '1', ' ');
        final Position pos = new Position(1, 1);

        for (final char symbol : invalidSymbols) {
            assertNull(
                entityFactory.createEntity(symbol, pos),
                "The invalid symbol '" + symbol + "' did not return null."
            );
        }
    }
}
