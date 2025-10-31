package it.unibo.elementsduo.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.elementsduo.controller.testDoubles.DoubleInputController;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

final class TestPlayer {

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


    @BeforeEach
    void setUp() {
        fireboy = new Fireboy(new Position(START_X, START_Y));
    }

    @Test
    void testGetPlayerType() {
        assertEquals(PlayerType.FIREBOY, fireboy.getPlayerType());
    }

    @Test
    void testMoveRight() {
        DoubleInputController input = new DoubleInputController();
        input.moveRight = true;

        fireboy.update(DELTA_TIME, input);

        final double expectedX = START_X + (RUN_SPEED * DELTA_TIME);
        assertEquals(expectedX, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    @Test
    void testMoveLeft() {
        DoubleInputController input = new DoubleInputController();
        input.moveLeft = true;

        fireboy.update(DELTA_TIME, input);

        final double expectedX = START_X - (RUN_SPEED * DELTA_TIME);
        assertEquals(expectedX, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    @Test
    void testNoMovement() {
        DoubleInputController input = new DoubleInputController();

        fireboy.update(DELTA_TIME, input);

        assertEquals(START_X, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }

    @Test
    void testJump() {
        DoubleInputController input = new DoubleInputController();
        input.jump = true;

        fireboy.update(DELTA_TIME, input);

        assertFalse(fireboy.isOnGround());
        final double expectedVy = -JUMP_STRENGTH + (GRAVITY * DELTA_TIME);
        assertEquals(expectedVy, fireboy.getVelocityY(), 1e-3);

        final double expectedY = START_Y + expectedVy * DELTA_TIME;
        assertEquals(expectedY, fireboy.getY(), 1e-3);
    }

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

    @Test
    void testStopJump() {
        fireboy.setAirborne();
        fireboy.setVelocityY(-5.0);

        double ceilingY = 7.0;
        fireboy.stopJump(ceilingY);

        assertEquals(ceilingY, fireboy.getY(), DELTA);
        assertEquals(0.0, fireboy.getVelocityY(), DELTA);
    }
    

    @Test
    void testSetVelocityY() {
        fireboy.setVelocityY(4.2);
        assertEquals(4.2, fireboy.getVelocityY(), DELTA);
    }

    @Test
    void testOnExit() {
        assertFalse(fireboy.isOnExit());
        fireboy.setOnExit(true);
        assertTrue(fireboy.isOnExit());
    }

    @Test
    void testCorrectPhysicsCollisionPositivePenetration() {
        double penetration = 0.5;
        Vector2D normal = new Vector2D(0.0, 1.0);

        double depth = Math.max(penetration - POSITION_SLOP, 0.0);
        double expectedY = START_Y + CORRECTION_PERCENT * depth;

        fireboy.correctPhysicsCollision(penetration, normal);

        assertEquals(expectedY, fireboy.getY(), DELTA);
    }

    @Test
    void testCorrectPhysicsCollisionNoPenetration() {
        double penetration = 0.0;
        Vector2D normal = new Vector2D(0.0, 1.0);

        fireboy.correctPhysicsCollision(penetration, normal);

        assertEquals(START_X, fireboy.getX(), DELTA);
        assertEquals(START_Y, fireboy.getY(), DELTA);
    }
}
