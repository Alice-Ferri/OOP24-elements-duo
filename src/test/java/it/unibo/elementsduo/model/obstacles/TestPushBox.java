package it.unibo.elementsduo.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
final class TestPushBoxPhysics {

    private static final double DELTA_TIME = 1.0;

    private PushBox box;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setUp() {
        this.box = new PushBox(new Position(0, 0));
        this.playerFactory = new PlayerFactoryImpl();
    }

    @Test
    void updateAppliesGravityInAir() {
        final double dt = DELTA_TIME / 10;
        box.update(dt);

        assertEquals(0.0, box.getVelocity().x());
        assertEquals(9.8 * dt, box.getVelocity().y());
        assertEquals(0.0, box.getCenter().x());
        assertEquals(9.8 * dt * dt, box.getCenter().y());
    }

    @Test
    void correctPhysicsCollisionFromSide() {
        box.push(new Vector2D(-3.0, 0.0));
        box.correctPhysicsCollision(0.3, new Vector2D(1.0, 0.0), new TestCollidable());

        assertEquals(0.0, box.getVelocity().x());
        assertEquals(0.0, box.getVelocity().y());
        final double expectedX = (0.3 - 0.001) * 0.8;
        assertEquals(expectedX, box.getCenter().x(), 1e-4);
    }

    @Test
    void maintainsHorizontalVelocityCollidingWithPlayer() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(0, 0));
        box.push(new Vector2D(-4.0, 0.0));
        box.correctPhysicsCollision(0.3, new Vector2D(-1.0, 0.0), player);

        assertEquals(-4.0, box.getVelocity().x());
        final double expectedX = -(0.3 - 0.001) * 0.8;
        assertEquals(expectedX, box.getCenter().x(), 1e-4);
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
