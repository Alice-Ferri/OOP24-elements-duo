package it.unibo.elementsduo.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Fireboy} and {@link Watergirl} classes.
 */
final class TestAllPlayer {

    private static final double START_X = 5.0;
    private static final double START_Y = 10.0;
    private static final double DELTA = 1e-6;
    private static final double DELTA_TIME = 0.1;
    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;
    private static final double GRAVITY = 9.8;
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENT = 0.8;

    private Fireboy fireboy;
    private Watergirl watergirl;

    /**
     * Sets up player instances before each test.
     */
    @BeforeEach
    void setUp() {
        fireboy = new Fireboy(new Position(START_X, START_Y));
        watergirl = new Watergirl(new Position(START_X, START_Y));
    }

    /**
     * Tests that Fireboy returns the correct player type.
     */
    @Test
    void testGetPlayerType() {
        assertEquals(PlayerType.FIREBOY, fireboy.getPlayerType());
        assertEquals(PlayerType.WATERGIRL, watergirl.getPlayerType());
    }

    /**
     * Tests horizontal movement to the right.
     */
    @Test
    void testMoveRight() {
        InputControllerTesting input = new InputControllerTesting();
        input.moveRight = true;

        fireboy.update(DELTA_TIME, input);

        final double expectedX = START_X + (RUN_SPEED * DELTA_TIME);
        assertEquals(expectedX, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    /**
     * Tests horizontal movement to the left.
     */
    @Test
    void testMoveLeft() {
        InputControllerTesting input = new InputControllerTesting();
        input.moveLeft = true;

        fireboy.update(DELTA_TIME, input);

        final double expectedX = START_X - (RUN_SPEED * DELTA_TIME);
        assertEquals(expectedX, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    /**
     * Tests that no input results in no horizontal movement.
     */
    @Test
    void testNoMovement() {
        InputControllerTesting input = new InputControllerTesting();

        fireboy.update(DELTA_TIME, input);

        assertEquals(START_X, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    /**
     * Tests jumping behavior, including vertical velocity and position change.
     */
    @Test
    void testJump() {
        InputControllerTesting input = new InputControllerTesting();
        input.jump = true;

        fireboy.update(DELTA_TIME, input);

        assertFalse(fireboy.isOnGround());
        final double expectedVy = -JUMP_STRENGTH + (GRAVITY * DELTA_TIME);
        assertEquals(expectedVy, fireboy.getVelocityY(), 1e-3);

        final double expectedY = START_Y + expectedVy * DELTA_TIME;
        assertEquals(expectedY, fireboy.getY(), 1e-3);
    }

    /**
     * Tests gravity application when player is airborne.
     */
    @Test
    void testApplyGravityWhenAirborne() {
        fireboy.setAirborne();
        double prevVy = fireboy.getVelocityY();
        double prevY = fireboy.getY();

        fireboy.applyGravity(GRAVITY);

        double expectedVy = prevVy + GRAVITY;
        double expectedY = prevY + expectedVy;

        assertEquals(expectedVy, fireboy.getVelocityY(), DELTA);
        assertEquals(expectedY, fireboy.getY(), DELTA);
    }

    /**
     * Tests landing on ground resets vertical velocity and sets onGround to true.
     */
    @Test
    void testLandOnGround() {
        fireboy.setAirborne();
        fireboy.setVelocityY(-5.0);

        double groundY = 3.0;
        fireboy.landOn(groundY);

        assertEquals(groundY, fireboy.getY(), DELTA);
        assertTrue(fireboy.isOnGround());
        assertEquals(0.0, fireboy.getVelocityY(), DELTA);
    }

    /**
     * Tests stopping jump resets vertical velocity.
     */
    @Test
    void testStopJump() {
        fireboy.setAirborne();
        fireboy.setVelocityY(-5.0);

        double ceilingY = 7.0;
        fireboy.stopJump(ceilingY);

        assertEquals(ceilingY, fireboy.getY(), DELTA);
        assertEquals(0.0, fireboy.getVelocityY(), DELTA);
    }

    /**
     * Tests setting vertical velocity directly.
     */
    @Test
    void testSetVelocityY() {
        fireboy.setVelocityY(4.2);
        assertEquals(4.2, fireboy.getVelocityY(), DELTA);
    }

    /**
     * Tests setting the onExit flag.
     */
    @Test
    void testOnExit() {
        assertFalse(fireboy.isOnExit());
        fireboy.setOnExit(true);
        assertTrue(fireboy.isOnExit());
    }

    /**
     * Tests physics collision correction with positive penetration.
     */
    @Test
    void testCorrectPhysicsCollisionPositivePenetration() {
        double penetration = 0.5;
        Vector2D normal = new Vector2D(0.0, 1.0);

        double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        double expectedY = START_Y + CORRECTION_PERCENT * depth;

        fireboy.correctPhysicsCollision(penetration, normal);

        assertEquals(expectedY, fireboy.getY(), DELTA);
    }

    /**
     * Tests that collision correction with zero penetration does nothing.
     */
    @Test
    void testCorrectPhysicsCollisionNoPenetration() {
        double penetration = 0.0;
        Vector2D normal = new Vector2D(0.0, 1.0);

        fireboy.correctPhysicsCollision(penetration, normal);

        assertEquals(START_X, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    /**
     * Test for Watergirl to verify type and initial position.
     */
    @Test
    void testWatergirlBasic() {
        assertEquals(PlayerType.WATERGIRL, watergirl.getPlayerType());

        assertEquals(START_X, watergirl.getX(), DELTA);
        assertEquals(START_Y, watergirl.getY(), DELTA);
    }
}
