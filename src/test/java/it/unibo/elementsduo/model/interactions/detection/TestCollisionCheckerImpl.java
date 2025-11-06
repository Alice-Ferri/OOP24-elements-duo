package it.unibo.elementsduo.model.interactions.detection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.interactions.detection.impl.CollisionCheckerImpl;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.interactions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.resources.Position;

final class TestCollisionCheckerImpl {

    private static final double EPSILON = 1e-9;
    private static final double EXP_1 = 0.6;
    private static final double EXP_2 = 0.4;

    private CollisionChecker checker;

    @BeforeEach
    void setUp() {
        this.checker = new CollisionCheckerImpl();
    }

    @Test
    void collisionBetweenOverlappingObjects() {
        final TestCollidable a = new TestCollidable(new Position(0, 0), 1.0, 1.0);
        final TestCollidable b = new TestCollidable(new Position(0.4, 0), 1.0, 1.0);

        final List<CollisionInformations> collisions = checker.checkCollisions(List.of(a, b));

        assertEquals(1, collisions.size());

        final CollisionInformations info = collisions.get(0);
        assertSame(a, info.getObjectA());
        assertSame(b, info.getObjectB());
        assertEquals(EXP_1, info.getPenetration(), EPSILON);
        assertEquals(-1.0, info.getNormal().x(), EPSILON);
        assertEquals(0.0, info.getNormal().y(), EPSILON);
    }

    @Test
    void separatedObjects() {
        final TestCollidable a = new TestCollidable(new Position(0, 0), 1.0, 1.0);
        final TestCollidable b = new TestCollidable(new Position(3, 0), 1.0, 1.0);

        final List<CollisionInformations> collisions = checker.checkCollisions(List.of(a, b));

        assertTrue(collisions.isEmpty());
    }

    @Test
    void objectsWithSameAxis() {
        final TestCollidable a = new TestCollidable(new Position(1.0, 1.0), 1.0, 1.0);
        final TestCollidable b = new TestCollidable(new Position(1.0, 1.6), 1.0, 1.0);

        final List<CollisionInformations> collisions = checker.checkCollisions(List.of(a, b));

        assertEquals(1, collisions.size());
        final CollisionInformations info = collisions.get(0);
        assertNotNull(info);
        assertEquals(EXP_2, info.getPenetration(), EPSILON);
        assertEquals(-1.0, info.getNormal().y(), EPSILON);
    }

    private static final class TestCollidable implements Collidable {
        private final HitBox hitBox;

        private TestCollidable(final Position center, final double width, final double height) {
            this.hitBox = new HitBoxImpl(center, height, width);
        }

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
