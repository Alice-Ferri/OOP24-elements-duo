package it.unibo.elementsduo.model.player;

import it.unibo.elementsduo.controller.inputcontroller.impl.InputControllerImpl;
import it.unibo.elementsduo.controller.inputcontroller.impl.InputState;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.solid.Wall;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of player class
 */
final class TestPlayer {

    private Player fireboy;
    private Player watergirl;
    private InputControllerImpl controller;

    @BeforeEach
    void setUp() {
        fireboy = new Fireboy(new Position(0, 0));
        watergirl = new Watergirl(new Position(0, 0));
        controller = new InputControllerImpl();
    }

    @Test
    void testInitialValuesForBothPlayers() {
        checkInitialValues(fireboy, PlayerType.FIREBOY);
        checkInitialValues(watergirl, PlayerType.WATERGIRL);
    }
    
    private void checkInitialValues(final Player player, final PlayerType playerType) {
        assertEquals(0.0, player.getX());
        assertEquals(0.0, player.getY());
        assertTrue(player.isOnGround());
        assertFalse(fireboy.isOnExit());
        assertEquals(new Vector2D(0, 0), player.getVelocity());
        assertEquals(playerType, player.getPlayerType());
    }

    @Test
    void testJumpAndLand() {
        fireboy.jump(6.5);
        assertFalse(fireboy.isOnGround());
        assertEquals(-6.5, fireboy.getVelocity().y());

        fireboy.landOn(10.0);
        assertTrue(fireboy.isOnGround());
        assertEquals(10.0, fireboy.getY());
        assertEquals(0.0, fireboy.getVelocity().y());
    }

    @Test
    void testStopJump() {
        fireboy.jump(5);
        fireboy.stopJump(3.0);
        assertEquals(3.0, fireboy.getY());
        assertEquals(0.0, fireboy.getVelocity().y());
    }

    @Test
    void testApplyGravity() {
        fireboy.setAirborne();
        fireboy.setVelocityY(0);
        fireboy.applyGravity(9.8);
        assertTrue(fireboy.getVelocity().y() > 0);
    }

    @Test
    void testSetters() {
        fireboy.setOnExit(true);
        assertTrue(fireboy.isOnExit());

        fireboy.setAirborne();
        assertFalse(fireboy.isOnGround());

        fireboy.setVelocityX(2.5);
        fireboy.setVelocityY(-1.5);
        assertEquals(2.5, fireboy.getVelocity().x());
        assertEquals(-1.5, fireboy.getVelocity().y());
    }

    @Test
    void testGetHitBox() {
        final HitBox hb = fireboy.getHitBox();
        assertNotNull(hb);
        assertEquals(hb.getCenter().x(), fireboy.getX());
        assertEquals(hb.getCenter().y(), fireboy.getY());
    }

    @Test
    void testUpdateAndHandleInputUsingRealController() {
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_D, 'D'));
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_W, 'W'));

        fireboy.update(0.016, controller);

        assertTrue(fireboy.getVelocity().x() > 0);
        assertFalse(fireboy.isOnGround());
        controller.markJumpHandled(PlayerType.FIREBOY);

        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_D, 'D'));
        controller.dispatchKeyEvent(new KeyEvent(new java.awt.Label(), KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_W, 'W'));

        assertFalse(controller.getInputState().isActionPressed(PlayerType.FIREBOY, InputState.Action.JUMP));
    }

    @Test
    void testCorrectPhysicsCollisionSimple() {
        final Collidable dummy = new Collidable() {
            @Override
            public HitBox getHitBox() {
                return fireboy.getHitBox();
            }

            @Override
            public CollisionLayer getCollisionLayer() {
                return fireboy.getCollisionLayer();
            }
        };
        fireboy.setVelocityY(-5);
        fireboy.correctPhysicsCollision(1.0, new Vector2D(0, 1), dummy);
        assertEquals(0.0, fireboy.getVelocity().y());
    }

    @Test
    void testCorrectPhysicsCollisionWithWall() {
        final HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1, 1);
        final AbstractStaticObstacle wall = new Wall(hitBox);
        fireboy.correctPhysicsCollision(1.0, new Vector2D(0, -1), wall);
    }

    @Test
    void testHandleVerticalWithPlatform() {
        final Position pos = new Position(0, 0);
        final Position a = new Position(0, 0);
        final Position b = new Position(0, 1);
        final PlatformImpl platform = new PlatformImpl(pos, a, b);
        platform.activate();
        platform.update(0.5);
        fireboy.correctPhysicsCollision(1.0, new Vector2D(0, -1), platform);
        assertTrue(fireboy.isOnGround());
        assertEquals(1.0, fireboy.getVelocity().y());
    }

    @Test
    void testNoCorrectionIfNoPenetration() {
        fireboy.correctPhysicsCollision(0, new Vector2D(1, 0), new Collidable() {
            @Override
            public HitBox getHitBox() {
                return fireboy.getHitBox();
            }

            @Override
            public CollisionLayer getCollisionLayer() {
                return fireboy.getCollisionLayer();
            }
        });
        assertEquals(0.0, fireboy.getX());
    }

    @Test
    void testIsImmuneToHazard() {
        assertTrue(fireboy.isImmuneTo(HazardType.LAVA));
        assertFalse(fireboy.isImmuneTo(HazardType.WATER));

        assertTrue(watergirl.isImmuneTo(HazardType.WATER));
        assertFalse(watergirl.isImmuneTo(HazardType.LAVA));
    }
}
