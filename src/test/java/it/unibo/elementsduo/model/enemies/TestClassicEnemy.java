package it.unibo.elementsduo.model.enemies;

import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.ClassicEnemiesImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.controller.enemiescontroller.api.EnemiesMoveManager;
import it.unibo.elementsduo.model.enemies.api.Enemy;

/**
 * Unit tests for the {@link ClassicEnemiesImpl} class, ensuring correct movement, 
 * state management, and collision response.
 */
final class ClassicEnemyTest {

    private static final double START_X = 20.0;
    private static final double START_Y = 15.0;
    private static final double CLASSIC_SPEED = 0.8;
    private static final double DELTA_TIME = 0.5;
    private static final double CORRECTION_PERCENT = 0.8;
    private static final double POSITION_SLOP = 0.001;

    private ClassicEnemiesImpl enemy;

    /**
     * Sets up the test environment before each test method runs.
     */
    @BeforeEach
    void setUp() {
        this.enemy = new ClassicEnemiesImpl(new Position(START_X, START_Y));
        this.enemy.setMoveManager(new ManualMoveManagerStub());
    }

    /**
     * Tests the initial state of the enemy (alive, position, and direction).
     */
    @Test
    void testInitialStateAndPosition() {
        assertTrue(enemy.isAlive());
        assertEquals(START_X, enemy.getX(), 0.001);
        assertEquals(START_Y, enemy.getY(), 0.001);
        assertEquals(1, enemy.getDirection(), 0.001);
    }

    /**
     * Tests that the enemy moves correctly based on its speed and delta time.
     */
    @Test
    void testMovementAndSpeed() {
        enemy.update(DELTA_TIME); 
        final double expectedX = START_X + (1 * CLASSIC_SPEED * DELTA_TIME);
        assertEquals(expectedX, enemy.getX(), 0.001);
    }
    
    /**
     * Tests that the classic enemy's attack method always returns an empty optional.
     */
    @Test
    void testAttackAlwaysEmpty() {
         final Optional<Projectiles> result = enemy.attack();
        assertFalse(result.isPresent());
    }

    /**
     * Tests the direction reversal method.
     */
    @Test
    void testDirectionReversal() {
        enemy.setDirection();
        assertEquals(-1, enemy.getDirection(), 0.001);
    }
    
    /**
     * Tests that a lateral physics collision inverts the enemy's direction.
     */
    @Test
    void testPhysicsCollisionInvertsDirection() {
        enemy.correctPhysicsCollision(0.1, new Vector2D(-1.0, 0.0),null);
        assertEquals(-1, enemy.getDirection(), 0.001);
    }
    
    /**
     * Tests that a physics collision correctly displaces the enemy along the normal.
     */
    @Test
    void testPhysicsCollisionCorrectsPosition() {
        final double penetration = 0.5;
        final Vector2D normal = new Vector2D(0.0, 1.0);
        
        final double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        final double expectedCorrectionY = normal.y() * CORRECTION_PERCENT * depth;
        final double expectedY = START_Y + expectedCorrectionY;

        enemy.correctPhysicsCollision(penetration, normal,null);
        
        assertEquals(expectedY, enemy.getY(), 0.001);
        // Direction should not change on vertical collision
        assertEquals(1, enemy.getDirection(), 0.001); 
    }

    /**
     * Tests that the die method correctly sets the enemy's state to inactive.
     */
    @Test
    void testDie() {
        enemy.die();
        assertFalse(enemy.isAlive());
    }

    /**
     * Minimal stub implementation of EnemiesMoveManager to prevent NullPointerException
     * during the update cycle.
     */
    private static final class ManualMoveManagerStub implements EnemiesMoveManager {
        @Override
        public void handleEdgeDetection(final Enemy enemy) { }

        public void notifyWallHit(final Enemy enemy, final Vector2D normal) { }
    }
}