package it.unibo.elementsduo.model.map.mapvalidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;

import it.unibo.elementsduo.model.gameentity.api.EntityFactory;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.gameentity.impl.EntityFactoryImpl;
import it.unibo.elementsduo.resources.Position;
import java.util.Set;

/**
 * Integration test for the {@link EntityFactoryImpl} class.
 * It verifies that all valid symbols create entities and invalid symbols return null.
 */
final class TestEntityFactoryImpl {

    private EntityFactory entityFactory;

    /**
     * Initializes the EntityFactory by injecting the implementations
     * of others factories before each test.
     */
    @BeforeEach
    void setUp() {
        final obstacleFactoryImpl obstacleFactory = new obstacleFactoryImpl();
        final EnemyFactoryImpl enemyFactory = new EnemyFactoryImpl();
        final InteractiveObstacleFactoryImpl interactiveObsFactory = new InteractiveObstacleFactoryImpl();

        this.entityFactory = new EntityFactoryImpl(
            obstacleFactory, 
            enemyFactory, 
            interactiveObsFactory
        );
    }

    /**
     * Tests that the constructor throws a NullPointerException
     * if any factory dependency is null.
     */
    @Test
    void testConstructorNullChecks() {
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(null, new EnemyFactoryImpl(), new InteractiveObstacleFactoryImpl()));
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(new obstacleFactoryImpl(), null, new InteractiveObstacleFactoryImpl()));
        assertThrows(NullPointerException.class, () -> 
            new EntityFactoryImpl(new obstacleFactoryImpl(), new EnemyFactoryImpl(), null));
    }

    /**
     * Tests that every single valid symbol
     * produces a non-null entity.
     */
    @Test
    void testAllValidSymbolsAreCreated() {
        final Set<Character> validSymbols = Set.of(
            'P', '#', 'A', 'F', 'Q', 'E', 'K',
            'B', 'W','C', 'S','L', 'H', 'M', 'R'
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