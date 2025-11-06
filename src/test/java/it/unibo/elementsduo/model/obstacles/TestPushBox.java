package it.unibo.elementsduo.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.interactions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Behavioural tests for {@link PushBox}.
 */
final class TestPushBox {

    private static final double DELTA_TIME = 1.0;
    private static final double GRAVITY = 9.8;
    private static final double TEST_PUSH_VELOCITY_X = -3.0;
    private static final double PLAYER_PUSH_VELOCITY_X = -4.0;
    private static final double TEST_PENETRATION_DEPTH = 0.3;
    private static final double ASSERT_TOLERANCE = 1e-4;
    private static final double TIME_DIVISOR = 10.0;
    private static final double POSITION_SLOP = 0.001;
    private static final double CORRECTION_PERCENTAGE = 0.8;

    private PushBox box;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        this.box = new PushBox(new Position(0, 0));
        this.playerFactory = new PlayerFactoryImpl();
    }

    @Test
    void updateAppliesGravityInAir() {
        final double dt = DELTA_TIME / TIME_DIVISOR;
        box.update(dt);

        assertEquals(0.0, box.getVelocity().x());
        assertEquals(GRAVITY * dt, box.getVelocity().y());
        assertEquals(0.0, box.getCenter().x());
        assertEquals(GRAVITY * dt * dt, box.getCenter().y());
    }

    @Test
    void correctPhysicsCollisionFromSide() {
        box.push(new Vector2D(TEST_PUSH_VELOCITY_X, 0.0));
        box.correctPhysicsCollision(TEST_PENETRATION_DEPTH, new Vector2D(1.0, 0.0), new TestCollidable());

        assertEquals(0.0, box.getVelocity().x());
        assertEquals(0.0, box.getVelocity().y());
        final double expectedX = (TEST_PENETRATION_DEPTH - POSITION_SLOP) * CORRECTION_PERCENTAGE;
        assertEquals(expectedX, box.getCenter().x(), ASSERT_TOLERANCE);
    }

    @Test
    void maintainsHorizontalVelocityCollidingWithPlayer() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(0, 0));
        box.push(new Vector2D(PLAYER_PUSH_VELOCITY_X, 0.0));
        box.correctPhysicsCollision(TEST_PENETRATION_DEPTH, new Vector2D(-1.0, 0.0), player);

        assertEquals(PLAYER_PUSH_VELOCITY_X, box.getVelocity().x());
        final double expectedX = -(TEST_PENETRATION_DEPTH - POSITION_SLOP) * CORRECTION_PERCENTAGE;
        assertEquals(expectedX, box.getCenter().x(), ASSERT_TOLERANCE);
    }

    /**
     * Simple static collidable Test.
     */
    private static final class TestCollidable implements Collidable {
        private final HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1.0, 1.0);

        @Override
        public HitBox getHitBox() {
            return this.hitBox;
        }

        @Override
        public CollisionLayer getCollisionLayer() {
            return CollisionLayer.STATIC_OBSTACLE;
        }
    }
}
