package it.unibo.elementsduo.model.player;

import it.unibo.elementsduo.controller.inputcontroller.impl.InputControllerImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
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

final class TestFireboy {

    private static final double CORRECTION_PERCENT = 0.8;
    private static final double POSITION_SLOP = 0.001;
    private static final double GRAVITY = 9.8;
    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;

    private PlayerFactory factory;
    private InputControllerImpl inputController;
    private Player fireboy;

    @BeforeEach
    void setUp() {
        factory = new PlayerFactoryImpl();
        inputController = new InputControllerImpl();
    }

    @Test
    void createFireboy() {
        final Position startPos = new Position(0, 0);
        fireboy = factory.createPlayer(PlayerType.FIREBOY, startPos);
        assertNotNull(fireboy, "Fireboy deve essere creato");
        assertEquals(startPos.x(), fireboy.getX());
        assertEquals(startPos.y(), fireboy.getY());
        assertTrue(fireboy instanceof Fireboy);
    }

    @Test
    void testPosition() {
        fireboy.correctPosition(5, 10);
        assertEquals(5, fireboy.getX());
        assertEquals(10, fireboy.getY());
    }

    @Test
    void testVelocity() {
        fireboy.setVelocityX(3);
        fireboy.setVelocityY(7);

        final Vector2D velocity = fireboy.getVelocity();

        assertEquals(3, velocity.x());
        assertEquals(7, velocity.y());
    }

    @Test
    void testGroundStates() {
        fireboy.setAirborne();
        assertFalse(fireboy.isOnGround());

        fireboy.setOnGround();
        assertTrue(fireboy.isOnGround());
    }

    @Test
    void testExitStates() {
        fireboy.setOnExit(true);
        assertTrue(fireboy.isOnExit());

        fireboy.setOnExit(false);
        assertFalse(fireboy.isOnExit());
    }

    @Test
    void testHitBox() {
        final HitBox hitBox = fireboy.getHitBox();
        assertNotNull(hitBox);
        assertEquals(fireboy.getHeight() / 2, hitBox.getHalfHeight());
        assertEquals(fireboy.getWidth() / 2, hitBox.getHalfWidth());
    }

    @Test
    void testCorrectPhysicsCollisionWithWall() {
        final HitBoxImpl wallHitBox = new HitBoxImpl(new Position(1, 0), 2, 2);
        final Wall wall = new Wall(wallHitBox);

        fireboy.correctPhysicsCollision(1.0, new Vector2D(1, 0), wall);

        final double depth = Math.max(1.0 - POSITION_SLOP, 0.0);
        final double expectedCorrectionX = 1 * CORRECTION_PERCENT * depth;

        assertEquals(expectedCorrectionX, fireboy.getX());

        assertEquals(0, fireboy.getVelocity().x());
        assertEquals(0, fireboy.getVelocity().y());
    }

    @Test
    void testCorrectPhysicsCollisionWithPlatform() {
        final Position startPos = new Position(0, -1);
        final Position targetA = new Position(0, -1);
        final Position targetB = new Position(0, -1);
        final PlatformImpl platform = new PlatformImpl(startPos, targetA, targetB);

        fireboy.correctPhysicsCollision(1.0, new Vector2D(0, -1), platform);

        assertTrue(fireboy.isOnGround());

        assertEquals(platform.getVelocity().y(), fireboy.getVelocity().y());

        assertEquals(0, fireboy.getVelocity().x());
    }

    @Test
    void testCollisionLayer() {
        assertEquals(CollisionLayer.PLAYER, fireboy.getCollisionLayer());
    }

    @Test
    void testUpdateMoveRight() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_D, 'D'));

        fireboy.setOnGround();
        fireboy.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(RUN_SPEED, fireboy.getVelocity().x());
        assertEquals(expectedVelocityY, fireboy.getVelocity().y());

        final Vector2D pos = new Vector2D(0 + RUN_SPEED * deltaTime, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), fireboy.getX());
        assertEquals(pos.y(), fireboy.getY());
    }

    @Test
    void testUpdateMoveLeft() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_A, ' '));

        fireboy.setOnGround();
        fireboy.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(-RUN_SPEED, fireboy.getVelocity().x());
        assertEquals(expectedVelocityY, fireboy.getVelocity().y());

        final Vector2D pos = new Vector2D(0 + (-RUN_SPEED) * deltaTime, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), fireboy.getX());
        assertEquals(pos.y(), fireboy.getY());
    }

    @Test
    void testUpdateJump() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        fireboy.setOnGround();
        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_W, 'W'));

        fireboy.update(deltaTime, inputController);

        final double expectedVelocityY = -JUMP_STRENGTH + GRAVITY * deltaTime;
        assertEquals(0, fireboy.getVelocity().x());
        assertEquals(expectedVelocityY, fireboy.getVelocity().y());
        assertFalse(fireboy.isOnGround());

        final Vector2D pos = new Vector2D(0 + 0 * deltaTime, 0 + expectedVelocityY * deltaTime);
        assertEquals(pos.x(), fireboy.getX());
        assertEquals(pos.y(), fireboy.getY());
    }

    @Test
    void testUpdateNoInput() {
        final double deltaTime = 0.016;

        fireboy.setOnGround();
        fireboy.update(deltaTime, inputController);

        final double expectedVelocityY = 0 + GRAVITY * deltaTime;
        assertEquals(0, fireboy.getVelocity().x());
        assertEquals(expectedVelocityY, fireboy.getVelocity().y());

        final Vector2D pos = new Vector2D(0, expectedVelocityY * deltaTime);
        assertEquals(pos.x(), fireboy.getX());
        assertEquals(pos.y(), fireboy.getY());
    }

    @Test
    void testUpdateNonArrowKeyInput() {
        final Component dummy = new Label();
        final double deltaTime = 0.016;

        inputController.dispatchKeyEvent(new KeyEvent(dummy, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, 'A'));

        fireboy.setOnGround();
        fireboy.update(deltaTime, inputController);

        assertEquals(0, fireboy.getVelocity().x());
    }

    @Test
    void testGetPlayerAndExitType() {
        assertEquals(PlayerType.FIREBOY, fireboy.getPlayerType());
        assertEquals(ExitType.FIRE_EXIT, fireboy.getRequiredExitType());
    }

    @Test
    void testIsImmuneTo() {
        assertTrue(fireboy.isImmuneTo(HazardType.LAVA));
        assertFalse(fireboy.isImmuneTo(HazardType.WATER));
    }
}
