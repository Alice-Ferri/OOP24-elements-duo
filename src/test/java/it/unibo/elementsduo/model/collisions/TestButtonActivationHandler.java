package it.unibo.elementsduo.model.collisions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.ButtonActivationHandler;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Tests for {@link ButtonActivationHandler}.
 */
final class TestButtonActivationHandler {

    private PlayerFactory playerFactory;
    private ButtonActivationHandler handler;

    @BeforeEach
    void setUp() {
        this.playerFactory = new PlayerFactoryImpl();
        this.handler = new ButtonActivationHandler();
    }

    @Test
    void pressesOnceAndLeaves() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(0, 0));
        final TestPressable pressable = new TestPressable();

        handler.onUpdateStart();
        final var builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, pressable, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        assertEquals(1, pressable.getPressCount());
        assertTrue(pressable.isPressed());
        assertEquals(0, pressable.getReleaseCount());

        handler.onUpdateStart();
        handler.onUpdateEnd();

        assertEquals(1, pressable.getPressCount());
        assertEquals(1, pressable.getReleaseCount());
        assertFalse(pressable.isPressed());
    }

    @Test
    void pressingAgainAfterReleaseAnotherPress() {
        final Player player = playerFactory.createPlayer(PlayerType.WATERGIRL, new Position(0, 0));
        final TestPressable pressable = new TestPressable();

        handler.onUpdateStart();
        var builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, pressable, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        handler.onUpdateStart();
        handler.onUpdateEnd();

        handler.onUpdateStart();
        builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, pressable, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        assertEquals(2, pressable.getPressCount());
        assertEquals(1, pressable.getReleaseCount());
        assertTrue(pressable.isPressed());
    }

    private static final class TestPressable implements Pressable {
        private final HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1.0, 1.0);
        private int pressCount;
        private int releaseCount;
        private boolean pressed;

        @Override
        public HitBox getHitBox() {
            return this.hitBox;
        }

        @Override
        public CollisionLayer getCollisionLayer() {
            return CollisionLayer.BUTTON;
        }

        @Override
        public void press() {
            this.pressCount++;
            this.pressed = true;
        }

        @Override
        public void release() {
            this.releaseCount++;
            this.pressed = false;
        }

        int getPressCount() {
            return this.pressCount;
        }

        int getReleaseCount() {
            return this.releaseCount;
        }

        boolean isPressed() {
            return this.pressed;
        }
    }
}
