package it.unibo.elementsduo.model.collisions.detection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.detection.impl.CollisionCheckerImpl;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

final class CollisionCheckerImplTest {

    private CollisionChecker collisionChecker;

    @BeforeEach
    void setUp() {
        this.collisionChecker = new CollisionCheckerImpl();
    }

    @Test
    void testNoCollisionReturnsEmptyList() {
        final Collidable a = new SimpleCollidable(new Position(0, 0), 1, 1, CollisionLayer.PLAYER);
        final Collidable b = new SimpleCollidable(new Position(5, 0), 1, 1, CollisionLayer.ENEMY);

        final List<CollisionInformations> infos = this.collisionChecker.checkCollisions(List.of(a, b));

        assertTrue(infos.isEmpty(), "no collisions when hitboxes do not overlap");
    }

    @Test
    void testCollisionProducesCorrectPenetrationAndNormal() {
        final Collidable left = new SimpleCollidable(new Position(0, 0), 2, 2, CollisionLayer.PLAYER);
        final Collidable right = new SimpleCollidable(new Position(1.5, 0), 2, 2, CollisionLayer.ENEMY);

        final List<CollisionInformations> infos = this.collisionChecker.checkCollisions(List.of(left, right));

        assertEquals(1, infos.size(), "one collision should be detected");

        final CollisionInformations info = infos.get(0);
        assertEquals(left, info.getObjectA());
        assertEquals(right, info.getObjectB());
        assertEquals(0.5, info.getPenetration(), 1e-6);
        assertEquals(new Vector2D(-1, 0), info.getNormal());
    }

    @Test
    void testVerticalCollisionChoosesSmallestAxisPenetration() {
        final Collidable bottom = new SimpleCollidable(new Position(0, 0), 2, 2, CollisionLayer.PLAYER);
        final Collidable top = new SimpleCollidable(new Position(0, -1.5), 2, 2, CollisionLayer.STATIC_OBSTACLE);

        final List<CollisionInformations> infos = this.collisionChecker.checkCollisions(List.of(bottom, top));

        assertEquals(1, infos.size());
        final CollisionInformations info = infos.get(0);
        assertEquals(bottom, info.getObjectA());
        assertEquals(top, info.getObjectB());
        assertEquals(0.5, info.getPenetration(), 1e-6);
        assertEquals(new Vector2D(0, 1), info.getNormal());
    }

    private static final class SimpleCollidable implements Collidable {

        private final HitBox hitBox;
        private final CollisionLayer layer;

        SimpleCollidable(final Position center, final double height, final double width, final CollisionLayer layer) {
            this.hitBox = new HitBoxImpl(center, height, width);
            this.layer = layer;
        }

        @Override
        public HitBox getHitBox() {
            return this.hitBox;
        }

        @Override
        public CollisionLayer getCollisionLayer() {
            return this.layer;
        }
    }
}
