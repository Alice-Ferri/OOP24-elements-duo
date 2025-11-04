package it.unibo.elementsduo.model.player;

import it.unibo.elementsduo.controller.inputcontroller.impl.InputControllerImpl;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.ExitType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.Label;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

final class TestWatergirl {

    private static final double CORRECTION_PERCENT = 0.8;
    private static final double POSITION_SLOP = 0.001;
    private static final double GRAVITY = 9.8;
    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;

    private Watergirl watergirl;
    private InputControllerImpl inputController;

    @BeforeEach
    void setUp() {
        watergirl = new Watergirl(new Position(0, 0));
        inputController = new InputControllerImpl();
    }

    @Test
    void testPosition() {
        watergirl.correctPosition(5, 10);
        assertEquals(5, watergirl.getX());
        assertEquals(10, watergirl.getY());
    }

    @Test
    void testVelocity() {
        watergirl.setVelocityX(3);
        watergirl.setVelocityY(7);

        final Vector2D velocity = watergirl.getVelocity();

        assertEquals(3, velocity.x());
        assertEquals(7, velocity.y());
    }

    @Test
    void testGroundStates() {
        watergirl.setAirborne();
        assertFalse(watergirl.isOnGround());

        watergirl.setOnGround();
        assertTrue(watergirl.isOnGround());
    }

    @Test
    void testExitStates() {
        watergirl.setOnExit(true);
        assertTrue(watergirl.isOnExit());

        watergirl.setOnExit(false);
        assertFalse(watergirl.isOnExit());
    }

    @Test
    void testPowerUp() {
        final PowerUpType powerUp = PowerUpType.HAZARD_IMMUNITY;

        watergirl.addPowerUpEffect(powerUp);
        assertTrue(watergirl.hasPowerUpEffect(powerUp));

        watergirl.removePowerUpEffect(powerUp);
        assertFalse(watergirl.hasPowerUpEffect(powerUp));
    }

    @Test
    void testHitBox() {
        final HitBox hitBox = watergirl.getHitBox();
        assertNotNull(hitBox);
        assertEquals(watergirl.getHeight() / 2, hitBox.getHalfHeight());
        assertEquals(watergirl.getWidth() / 2, hitBox.getHalfWidth());
    }

    @Test
    void testCorrectPhysicsCollisionWithWall() {
        final HitBoxImpl wallHitBox = new HitBoxImpl(new Position(1, 0), 2, 2);
        final Wall wall = new Wall(wallHitBox);

        watergirl.correctPhysicsCollision(1.0, new Vector2D(1, 0), wall);

        final double depth = Math.max(1.0 - POSITION_SLOP, 0.0);
        final double expectedCorrectionX = 1 * CORRECTION_PERCENT * depth;

        assertEquals(expectedCorrectionX, watergirl.getX());

        assertEquals(0, watergirl.getVelocity().x());
        assertEquals(0, watergirl.getVelocity().y());
    }

    @Test
    void testCorrectPhysicsCollisionWithPlatform() {
        final Position startPos = new Position(0, -1);
        final Position targetA = new Position(0, -1);
        final Position targetB = new Position(0, -1);
        final PlatformImpl platform = new PlatformImpl(startPos, targetA, targetB);

        watergirl.correctPhysicsCollision(1.0, new Vector2D(0, -1), platform);

        assertTrue(watergirl.isOnGround());

        assertEquals(platform.getVelocity().y(), watergirl.getVelocity().y());

        assertEquals(0, watergirl.getVelocity().x());
    }

    @Test
    void testCollisionLayer() {
        assertEquals(CollisionLayer.PLAYER, watergirl.getCollisionLayer());
    }

    @Test
    void testUpdateMoveRight() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_RIGHT, ' '));

        watergirl.setOnGround();
        watergirl.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(RUN_SPEED, watergirl.getVelocity().x());
        assertEquals(expectedVelocityY, watergirl.getVelocity().y());

        final Vector2D pos = new Vector2D(0 + RUN_SPEED * deltaTime, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), watergirl.getX());
        assertEquals(pos.y(), watergirl.getY());
    }

    @Test
    void testUpdateMoveLeft() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, ' '));

        watergirl.setOnGround();
        watergirl.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(-RUN_SPEED, watergirl.getVelocity().x());
        assertEquals(expectedVelocityY, watergirl.getVelocity().y());

        final Vector2D pos = new Vector2D(0 + (-RUN_SPEED) * deltaTime, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), watergirl.getX());
        assertEquals(pos.y(), watergirl.getY());
    }

    @Test
    void testUpdateJump() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        watergirl.setOnGround();
        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_UP, ' '));

        watergirl.update(deltaTime, inputController);

        final double expectedVelocityY = -JUMP_STRENGTH + GRAVITY * deltaTime;
        assertEquals(0, watergirl.getVelocity().x());
        assertEquals(expectedVelocityY, watergirl.getVelocity().y());
        assertFalse(watergirl.isOnGround());

        final Vector2D pos = new Vector2D(0, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), watergirl.getX());
        assertEquals(pos.y(), watergirl.getY());
    }

    @Test
    void testUpdateNoInput() {
        final double deltaTime = 0.016;

        watergirl.setOnGround();
        watergirl.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(0, watergirl.getVelocity().x());
        assertEquals(expectedVelocityY, watergirl.getVelocity().y());

        final Vector2D pos = new Vector2D(0, expectedVelocityY * deltaTime);
        assertEquals(pos.x(), watergirl.getX());
        assertEquals(pos.y(), watergirl.getY());
    }

    @Test
    void testUpdateNonArrowKeyInput() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_A, 'A'));

        watergirl.setOnGround();
        watergirl.update(deltaTime, inputController);

        assertEquals(0, watergirl.getVelocity().x());
    }

    @Test
    void testGetPlayerAndExitType() {
        assertEquals(PlayerType.WATERGIRL, watergirl.getPlayerType());
        assertEquals(ExitType.WATER_EXIT, watergirl.getRequiredExitType());
    }

    @Test
    void testIsImmuneTo() {
        assertTrue(watergirl.isImmuneTo(HazardType.WATER));
        assertFalse(watergirl.isImmuneTo(HazardType.LAVA));
    }
}
