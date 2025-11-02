package it.unibo.elementsduo.model.player;

import it.unibo.elementsduo.controller.inputController.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.inputController.impl.InputState;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;
import it.unibo.elementsduo.model.obstacles.api.obstacle;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di integrazione per {@link Fireboy} senza uso di mock.
 */
class FireboyTest {

    private Fireboy player;
    private InputControllerImpl controller;

    @BeforeEach
    void setUp() {
        player = new Fireboy(new Position(0, 0));
        controller = new InputControllerImpl();
    }

    @Test
    void testInitialValues() {
        assertEquals(0.0, player.getX());
        assertEquals(0.0, player.getY());
        assertTrue(player.isOnGround());
        assertFalse(player.isOnExit());
        assertEquals(new Vector2D(0, 0), player.getVelocity());
        assertEquals(PlayerType.FIREBOY, player.getPlayerType());
    }

    @Test
    void testMove() {
        player.move(2.0);
        assertEquals(2.0, player.getX());
    }

    @Test
    void testJumpAndLand() {
        player.jump(6.5);
        assertFalse(player.isOnGround());
        assertEquals(-6.5, player.getVelocity().y());

        player.landOn(10.0);
        assertTrue(player.isOnGround());
        assertEquals(10.0, player.getY());
        assertEquals(0.0, player.getVelocity().y());
    }

    @Test
    void testStopJump() {
        player.jump(5);
        player.stopJump(3.0);
        assertEquals(3.0, player.getY());
        assertEquals(0.0, player.getVelocity().y());
    }

    @Test
    void testApplyGravity() {
        player.setAirborne();
        player.setVelocityY(0);
        player.applyGravity(9.8);
        assertTrue(player.getVelocity().y() > 0);
    }

    @Test
    void testSetters() {
        player.setOnExit(true);
        assertTrue(player.isOnExit());

        player.setAirborne();
        assertFalse(player.isOnGround());

        player.setVelocityX(2.5);
        player.setVelocityY(-1.5);
        assertEquals(2.5, player.getVelocity().x());
        assertEquals(-1.5, player.getVelocity().y());
    }

    @Test
    void testGetHitBox() {
        HitBox hb = player.getHitBox();
        assertNotNull(hb);
        assertEquals(hb.getCenter().x(), player.getX());
        assertEquals(hb.getCenter().y(), player.getY());
    }

    @Test
    void testUpdateAndHandleInputUsingRealController() {
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_D, 'D'));
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_W, 'W'));

        player.update(0.016, controller);

        assertTrue(player.getVelocity().x() > 0);
        assertFalse(player.isOnGround());
        controller.markJumpHandled(PlayerType.FIREBOY);

        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_D, 'D'));
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_W, 'W'));

        assertFalse(controller.getInputState().isActionPressed(PlayerType.FIREBOY, InputState.Action.JUMP));
    }

    @Test
    void testCorrectPhysicsCollisionSimple() {
        Collidable dummy = new Collidable() {
            @Override
            public HitBox getHitBox() {
                return player.getHitBox();
            }
        };
        player.setVelocityY(-5);
        player.correctPhysicsCollision(1.0, new Vector2D(0, 1), dummy);
        assertEquals(0.0, player.getVelocity().y());
    }

    @Test
    void testCorrectPhysicsCollisionWithWall() {
        HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1, 1);
        AbstractStaticObstacle wall = new Wall(hitBox);
        player.correctPhysicsCollision(1.0, new Vector2D(0, -1), wall);
    }

    @Test
    void testHandleVerticalWithPlatform() {
        Position pos = new Position(0, 0);
        Position a = new Position(0, 0);
        Position b = new Position(0, 1);
        obstacle platform = new PlatformImpl(pos, a, b);
        player.correctPhysicsCollision(1.0, new Vector2D(0, -1), platform);
        assertTrue(player.isOnGround());
        assertEquals(3.0, player.getVelocity().y());
    }

    @Test
    void testNoCorrectionIfNoPenetration() {
        player.correctPhysicsCollision(0, new Vector2D(1, 0), new Collidable() {
            @Override
            public HitBox getHitBox() {
                return player.getHitBox();
            }
        });
        assertEquals(0.0, player.getX());
    }

    @Test
    void testIsImmuneToHazard() {
        assertTrue(player.isImmuneTo(HazardType.LAVA));
        assertFalse(player.isImmuneTo(HazardType.WATER));
    }
}
