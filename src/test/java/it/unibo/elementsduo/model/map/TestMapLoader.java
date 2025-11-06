package it.unibo.elementsduo.model.map;

import it.unibo.elementsduo.model.map.level.MapLoader;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.Button;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.impl.FireExit;
import it.unibo.elementsduo.model.obstacles.staticobstacles.exitzone.impl.WaterExit;
import it.unibo.elementsduo.model.obstacles.staticobstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.staticobstacles.solid.Wall;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;

import java.util.Set;

/**
 * Test for the {@link MapLoader} class.
 * Verifies file loading and entity creation.
 */
final class TestMapLoader {

    private static final int EXPECTED_WALLS = 35;
    private static final int LEVER_Y_POSITION = 5;

    private MapLoader mapLoader;
    private Set<GameEntity> gameEntities;

    /**
     * Sets up the MapLoader with its real factories before each test.
     */
    @BeforeEach
    void setUp() {
        this.mapLoader = new MapLoader();

        gameEntities = assertDoesNotThrow(() -> this.mapLoader.loadLevelFromFile("test/maploader.txt"));
    }

    /**
     * Tests that loading a non-existent file
     * throws the correct {@link IllegalArgumentException}.
     */
    @Test
    void testLoadMissingFile() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.mapLoader.loadLevelFromFile("test/nonexistmap.txt");
        }, "Loading a non-existent file should throw an exception");
    }

    /**
     * Tests the correct loading and creation of all entities.
     * Loads a map that contains an instance of every entity type.
     */
    @Test
    void testMapLoadingAndEntityCreation() {

        assertNotNull(gameEntities);
        assertTrue(countEntities(Obstacle.class) > 0, "No obstacles were loaded");

        assertEquals(1, countEntities(Fireboy.class));
        assertEquals(1, countEntities(Watergirl.class));
        assertEquals(1, countEntities(FireExit.class));
        assertEquals(1, countEntities(WaterExit.class));
        assertEquals(1, countEntities(Floor.class));
        assertEquals(1, countEntities(Lever.class));
        assertEquals(1, countEntities(Button.class));
        assertEquals(1, countEntities(PlatformImpl.class));
        assertEquals(1, countEntities(PushBox.class));
        assertEquals(2, countEntities(Enemy.class));
        assertEquals(EXPECTED_WALLS, countEntities(Wall.class));
    }

    /**
     * Tests the correct positioning of a specific entity.
     */
    @Test
    void testEntityPositioning() {

        final Fireboy fb = findEntity(Fireboy.class);
        assertNotNull(fb, "Fireboy not found in Set");
        assertEquals(new Position(1, 1), fb.getHitBox().getCenter());

        final Lever lever = findEntity(Lever.class);
        assertNotNull(lever, "Lever not found in Set");
        assertEquals(new Position(1, LEVER_Y_POSITION), lever.getHitBox().getCenter());
    }

    /**
     * Helper method to count entities of a certain type in the Set.
     *
     * @param <T>  The type of entity to count.
     * @param type The class of the entity to count.
     * @return The number of instances of that type.
     */
    private <T> long countEntities(final Class<T> type) {
        return this.gameEntities.stream()
                .filter(type::isInstance)
                .count();
    }

    /**
     * Helper method to find the first instance of an entity in the set.
     *
     * @param <T>  The type of entity to find.
     * @param type The class of the entity to find.
     * @return The found entity, or null if not present.
     */
    private <T> T findEntity(final Class<T> type) {
        return this.gameEntities.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElse(null);
    }
}
