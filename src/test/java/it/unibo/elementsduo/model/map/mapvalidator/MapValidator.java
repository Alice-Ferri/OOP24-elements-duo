package it.unibo.elementsduo.model.map.mapvalidator;

import it.unibo.elementsduo.model.map.level.api.Level;
import it.unibo.elementsduo.model.map.level.impl.LevelImpl;
import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.map.mapvalidator.api.InvalidMapException;
import it.unibo.elementsduo.model.map.mapvalidator.api.MapValidator;
import it.unibo.elementsduo.model.map.mapvalidator.impl.MapValidatorImpl;

import it.unibo.elementsduo.model.enemies.impl.EnemyFactoryImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.obstacleFactoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@link MapValidatorImpl}.
 * Verifies that loaded maps are correctly validated against game rules.
 */
final class TestMapValidator {

    private MapValidator validator;
    private MapLoader mapLoader;

    /**
     * Sets up the validator and the MapLoader with its factories.
     */
    @BeforeEach
    void setUp() {
        this.validator = new MapValidatorImpl();
        this.mapLoader = new MapLoader(
            new obstacleFactoryImpl(), 
            new EnemyFactoryImpl(),
            new InteractiveObstacleFactoryImpl()
        );
    }

    /**
     * Tests the validation of a valid map loaded from a file.
     */
    @Test
    void testValidMapFromFile() {
        assertDoesNotThrow(() -> {
            final Level validLevel = new LevelImpl(mapLoader.loadLevelFromFile("test/valid_map.txt"));
            this.validator.validate(validLevel);
        }, "A valid map should not throw an exception");
    }

    /**
     * Tests if the validator detects unclosed boundaries from a file.
     */
    @Test
    void testInvalidBoundaryFromFile() {
        final var e = assertThrows(InvalidMapException.class, () -> {
            final Level invalidLevel = new LevelImpl(mapLoader.loadLevelFromFile("test/invalid_boundary.txt"));
            this.validator.validate(invalidLevel);
        });
        
        assertTrue(e.getMessage().contains("Boundary not closed"));
    }

    /**
     * Tests if the validator detects an unreachable exit from a file.
     */
    @Test
    void testUnreachableExitFromFile() {
        final var e = assertThrows(InvalidMapException.class, () -> {
            final Level invalidLevel = new LevelImpl(mapLoader.loadLevelFromFile("test/invalid_reach.txt"));
            this.validator.validate(invalidLevel);
        });
        
        assertTrue(e.getMessage().contains("cannot reach the exit"));
    }

    /**
     * Tests if the validator detects a floating enemy.
     */
    @Test
    void testFloatingEnemy() {
        final var e = assertThrows(InvalidMapException.class, () -> {
            final Level invalidLevel = new LevelImpl(mapLoader.loadLevelFromFile("test/floating_enemies.txt"));
            this.validator.validate(invalidLevel);
        });
        
        assertTrue(e.getMessage().contains("Enemy at"));
    }

    /**
     * Tests if the validator detects a floating interactive object.
     */
    @Test
    void testFloatingInteractive() {
        final var e = assertThrows(InvalidMapException.class, () -> {
            final Level invalidLevel = new LevelImpl(mapLoader.loadLevelFromFile("test/floating_interactive.txt"));
            this.validator.validate(invalidLevel);
        });
        
        assertTrue(e.getMessage().contains("The object"));
    }

}