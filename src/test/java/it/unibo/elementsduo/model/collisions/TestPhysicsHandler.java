package it.unibo.elementsduo.model.collisions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.core.impl.handlers.PhysicsHandler;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

public class TestPhysicsHandler {
    private static final double EPSILON = 1e-9;

    private PhysicsHandler handler;

    @BeforeEach
    void setUp() {
        this.handler = new PhysicsHandler();
    }

    @Test
    void appliesCorrectionAgainstStaticCollidable() {
        final TestMovable movable = new TestMovable();
        final Collidable wall = new TestStaticCollidable();

        final var info = new CollisionInformationsImpl(movable, wall, 1.0, new Vector2D(0, -1));
        final var builder = new CollisionResponse.Builder();

        handler.handle(info, builder);
        builder.build().execute();

        assertEquals(1, movable.calls);
        assertEquals(1.0, movable.lastPenetration, EPSILON);
        assertEquals(0.0, movable.lastNormal.x(), EPSILON);
        assertEquals(-1.0, movable.lastNormal.y(), EPSILON);
        assertSame(wall, movable.lastOther);
    }

    @Test
    void splitsPenetrationBetweenTwoMovables() {
        final TestMovable left = new TestMovable();
        final TestMovable right = new TestMovable();

        final var info = new CollisionInformationsImpl(left, right, 2.0, new Vector2D(1, 0));
        final var builder = new CollisionResponse.Builder();

        handler.handle(info, builder);
        builder.build().execute();

        assertEquals(1, left.calls);
        assertEquals(1, right.calls);

        assertEquals(1.0, left.lastPenetration, EPSILON);
        assertEquals(1.0, left.lastNormal.x(), EPSILON);
        assertEquals(0.0, left.lastNormal.y(), EPSILON);

        assertEquals(1.0, right.lastPenetration, EPSILON);
        assertEquals(-1.0, right.lastNormal.x(), EPSILON);
        assertEquals(0.0, right.lastNormal.y(), EPSILON);
    }

    @Test
    void skipsHandlerWhenNoPhysicsResponse() {
        final TestMovable movable = new TestMovable();
        movable.setState(false);
        final Collidable wall = new TestStaticCollidable();

        assertFalse(handler.canHandle(movable, wall));
    }

    private static final class TestMovable implements Movable {

        private final HitBox hitBox = new HitBoxImpl(new Position(0, 0), 1.0, 1.0);

        private int calls;
        private double lastPenetration;
        private Vector2D lastNormal;
        private Collidable lastOther;
        private boolean state = true;

        @Override
        public HitBox getHitBox() {
            return this.hitBox;
        }

        @Override
        public CollisionLayer getCollisionLayer() {
            return CollisionLayer.PUSHABLE;
        }

        @Override
        public void correctPhysicsCollision(final double penetration, final Vector2D normal,
                final Collidable other) {
            this.calls++;
            this.lastPenetration = penetration;
            this.lastNormal = normal;
            this.lastOther = other;
        }

        @Override
        public boolean resolvePhysicsWith(final Collidable other) {
            return this.state;
        }

        @Override
        public boolean hasPhysicsResponse() {
            return true;
        }

        public void setState(boolean state) {
            this.state = state;
        }

    }

    private static final class TestStaticCollidable implements Collidable {

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
