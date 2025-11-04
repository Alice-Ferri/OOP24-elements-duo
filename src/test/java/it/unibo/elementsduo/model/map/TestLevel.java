package it.unibo.elementsduo.model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.gameentity.api.GameEntity;
import it.unibo.elementsduo.model.map.level.impl.Level;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.resources.Position;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.Lever;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.model.enemies.impl.ProjectilesImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the {@link LevelImpl} class using real entity implementations.
 * It verifies that entity filtering, state management,
 * and encapsulation work correctly.
 */
final class TestLevel {

    private static final int POS_NINE = 9;
    private static final int POS_FIVE = 5;

    private Level level;
    private Set<GameEntity> originalEntities;
    private Player player1;
    private Player player2;
    private Enemy enemyAlive;
    private Enemy enemyDead;
    private Projectiles projActive;
    private Projectiles projInactive;
    private Obstacle obstacle;
    private Obstacle interactive;

    /**
     * Initializes a Level with a predefined set.
     */
    @BeforeEach
    void setUp() {
        originalEntities = new HashSet<>();

        enemyAlive = new ClassicEnemiesImpl(new Position(2, 1)); 
        enemyDead = new ClassicEnemiesImpl(new Position(2, 2));
        enemyDead.die(); 

        projActive = new ProjectilesImpl(new Position(3, 1), 1);
        projInactive = new ProjectilesImpl(new Position(3, 2), -1);
        projInactive.deactivate(); 

        obstacle = new Wall(new HitBoxImpl(new Position(1, 3), 1, 1));
        interactive = new Lever(new Position(POS_FIVE, 1));

        originalEntities.add(player1);
        originalEntities.add(player2);
        originalEntities.add(enemyAlive);
        originalEntities.add(enemyDead);
        originalEntities.add(projActive);
        originalEntities.add(projInactive);
        originalEntities.add(obstacle);
        originalEntities.add(interactive);

        this.level = new Level(originalEntities);
    }

    /**
     * Tests the constructor for null-checking
     * and for performing a defensive copy of the Set.
     */
    @Test
    void testConstructor() {
        assertThrows(NullPointerException.class, () -> new Level(null));

        final Set<GameEntity> initialSet = new HashSet<>();
        final Level testLevel = new Level(initialSet);

        assertEquals(1, testLevel.getGameEntities().size(), 
            "The constructor must create a defensive copy of the Set.");
    }

    /**
     * Tests getGameEntities() and its unmodifiable nature.
     */
    @Test
    void testGetGameEntities() {
        final Set<GameEntity> entities = level.getGameEntities();

        assertEquals(originalEntities.size(), entities.size());
        assertTrue(entities.containsAll(originalEntities));

        assertThrows(UnsupportedOperationException.class, () -> { }, 
            "The set returned by getGameEntities must be unmodifiable.");
    }

    /**
     * Tests the central getEntitiesByClass() method
     * and its unmodifiable nature.
     */
    @Test
    void testGetEntitiesByClass() {
        final Set<Player> players = level.getEntitiesByClass(Player.class);
        assertEquals(2, players.size());
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));

        final Set<Enemy> enemies = level.getEntitiesByClass(Enemy.class);
        assertEquals(2, enemies.size());

        final Set<Fireboy> fireboys = level.getEntitiesByClass(Fireboy.class);
        assertEquals(1, fireboys.size());

        final Set<Lever> levers = level.getEntitiesByClass(Lever.class);
        assertEquals(1, levers.size());

        assertThrows(UnsupportedOperationException.class, () -> { }, 
            "The set returned by getEntitiesByClass must be unmodifiable.");
    }

    /**
     * Tests the helper methods that filter by type e.g., getAllPlayers.
     */
    @Test
    void testGetAllHelperMethods() {
        assertEquals(2, level.getAllObstacles().size()); 
        assertTrue(level.getAllObstacles().contains(obstacle));
        assertTrue(level.getAllObstacles().contains(interactive));

        assertEquals(2, level.getAllEnemies().size());
        assertEquals(2, level.getAllPlayers().size());
        assertEquals(1, level.getAllInteractiveObstacles().size());
        assertEquals(2, level.getAllProjectiles().size());
    }

    /**
     * Tests helper methods that filter by state like getLivingEnemies.
     */
    @Test
    void testGetByStateHelperMethods() {
        final Set<Enemy> living = level.getLivingEnemies();
        assertEquals(1, living.size());
        assertTrue(living.contains(enemyAlive));
        assertFalse(living.contains(enemyDead));

        final List<Collidable> collidables = level.getAllCollidables();
        assertEquals(originalEntities.size(), collidables.size());
    }

    /**
     * Tests the mutation methods: addProjectile, cleanProjectiles, cleanInactiveEntities.
     */
    @Test
    void testMutationMethods() {
        final Projectiles newProjectile = new ProjectilesImpl(new Position(POS_NINE, POS_NINE), 1);
        level.addProjectile(newProjectile);

        final int expectedSizeAfterAdd = originalEntities.size() + 1;
        assertEquals(expectedSizeAfterAdd, level.getGameEntities().size());
        assertEquals(3, level.getAllProjectiles().size());

        level.cleanProjectiles();
        assertEquals(2, level.getAllProjectiles().size(), "Should only remove projInactive");
        assertTrue(level.getAllProjectiles().contains(projActive));
        assertTrue(level.getAllProjectiles().contains(newProjectile));

        setUp();
        assertEquals(originalEntities.size(), level.getGameEntities().size());

        level.cleanInactiveEntities();

        final int expectedSizeAfterClean = originalEntities.size() - 2;
        assertEquals(expectedSizeAfterClean, level.getGameEntities().size());

        assertFalse(level.getAllEnemies().contains(enemyDead), "enemyDead should be removed");
        assertTrue(level.getAllEnemies().contains(enemyAlive), "enemyAlive should remain");

        assertFalse(level.getAllProjectiles().contains(projInactive), "projInactive should be removed");
        assertTrue(level.getAllProjectiles().contains(projActive), "projActive should remain");
    }
}
