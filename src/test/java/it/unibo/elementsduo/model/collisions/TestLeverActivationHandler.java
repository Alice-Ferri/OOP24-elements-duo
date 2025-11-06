package it.unibo.elementsduo.model.collisions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.LeverActivationHandler;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.api.Toggler;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerFactory;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.impl.PlayerFactoryImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Tests for {@link LeverActivationHandler}.
 */
final class TestLeverActivationHandler {

    private PlayerFactory playerFactory;
    private LeverActivationHandler handler;

    @BeforeEach
    void setUp() {
        this.playerFactory = new PlayerFactoryImpl();
        this.handler = new LeverActivationHandler();
    }

    @Test
    void togglesOncePerContact() {
        final Player player = playerFactory.createPlayer(PlayerType.FIREBOY, new Position(0, 0));
        final TestToggler lever = new TestToggler();

        // first collision in frame
        handler.onUpdateStart();
        var builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, lever, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        assertEquals(1, lever.toggleCount);
        assertTrue(lever.currentState);

        // no more toggle
        handler.onUpdateStart();
        builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, lever, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        assertEquals(1, lever.toggleCount);
        assertTrue(lever.currentState);

        handler.onUpdateStart();
        handler.onUpdateEnd();

        // toggle again
        handler.onUpdateStart();
        builder = new CollisionResponse.Builder();
        handler.handle(new CollisionInformationsImpl(player, lever, 0.0, Vector2D.ZERO), builder);
        builder.build().execute();
        handler.onUpdateEnd();

        assertEquals(2, lever.toggleCount);
        assertFalse(lever.currentState);
        assertTrue(lever.previousState);
    }

    private static final class TestToggler implements Toggler {
        private final HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1.0, 1.0);
        private int toggleCount;
        private boolean currentState;
        private boolean previousState;

        @Override
        public HitBox getHitBox() {
            return this.hitBox;
        }

        @Override
        public CollisionLayer getCollisionLayer() {
            return CollisionLayer.LEVER;
        }

        @Override
        public void toggle() {
            this.toggleCount++;
            this.previousState = this.currentState;
            this.currentState = !this.currentState;
        }

    }
}
