package it.unibo.elementsduo.model.enemies;

import it.unibo.elementsduo.model.enemies.impl.ProjectilesImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for the {@link ProjectilesImpl} class, ensuring correct movement, 
 * state management, and collision physics.
 */
final class TestProjectilesImpl {

    private static final double START_X = 5.0;
    private static final double START_Y = 10.0;
    private static final double DIRECTION_RIGHT = 1;
    private static final double DIRECTION_LEFT = -1;
    private static final double SPEED = 5.0;
    private static final double SIZE = 0.25;
    private static final double DELTA_TIME = 0.1;
    private static final double DELTA = 0.001;

    private ProjectilesImpl projectile;

    /**
     * Sets up the test environment before each test method runs.
     */
    @BeforeEach
    void setUp() {
        this.projectile = new ProjectilesImpl(new Position(START_X, START_Y), (int) DIRECTION_RIGHT);
    }

    /**
     * Tests the initial state of the projectile (active, position, and direction).
     */
    @Test
    void testInitialStateAndPosition() {
        assertTrue(projectile.isActive());
        assertEquals(START_X, projectile.getX(), DELTA);
        assertEquals(START_Y, projectile.getY(), DELTA);
        assertEquals(DIRECTION_RIGHT, projectile.getDirection(), DELTA);
    }

    /**
     * Tests that the projectile's state is correctly set to inactive.
     */
    @Test
    void testDeactivate() {
        projectile.deactivate();
        assertFalse(projectile.isActive());
    }

    /**
     * Tests the horizontal movement when the direction is positive (right).
     */
    @Test
    void testMovementToRight() {
        projectile.update(DELTA_TIME); 
        final double expectedX = START_X + (DIRECTION_RIGHT * SPEED * DELTA_TIME);
        assertEquals(expectedX, projectile.getX(), DELTA);
        assertEquals(START_Y, projectile.getY(), DELTA);
    }

    /**
     * Tests the horizontal movement when the direction is negative (left).
     */
    @Test
    void testMovementToLeft() {
        final ProjectilesImpl leftProjectile = new ProjectilesImpl(new Position(START_X, START_Y), (int) DIRECTION_LEFT);
        
        leftProjectile.update(DELTA_TIME); 
        
        final double expectedX = START_X + (DIRECTION_LEFT * SPEED * DELTA_TIME);
        assertEquals(expectedX, leftProjectile.getX(), DELTA);
    }

    /**
     * Tests that a collision with positive penetration correctly displaces the projectile.
     */
    @Test
    void testCorrectPhysicsCollisionCorrection() {
        final double penetration = 0.5;
        final Vector2D normal = new Vector2D(0.0, 1.0);
        
        final double correctionPerc = 0.8;
        final double positionSlop = 0.001;
        final double depth = penetration - positionSlop;
        final double expectedCorrectionY = normal.y() * correctionPerc * depth;

        projectile.correctPhysicsCollision(penetration, normal, null);

        assertEquals(START_Y + expectedCorrectionY, projectile.getY(), DELTA);
        assertTrue(projectile.isActive());
    }
    
    /**
     * Tests that a collision with zero penetration results in no change to position.
     */
    @Test
    void testCorrectPhysicsCollisionNoCorrection() {
        final double penetration = 0.0;
        final Vector2D normal = new Vector2D(1.0, 0.0); 
        
        projectile.correctPhysicsCollision(penetration, normal, null);

        assertEquals(START_X, projectile.getX(), DELTA);
        assertEquals(START_Y, projectile.getY(), DELTA);
    }
}