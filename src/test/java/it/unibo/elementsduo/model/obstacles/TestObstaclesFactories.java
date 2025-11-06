package it.unibo.elementsduo.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.impl.InteractiveObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.impl.ObstacleFactoryImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PlatformImpl;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PushBox;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api.Hazard;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.api.ObstacleFactory;
import it.unibo.elementsduo.model.obstacles.impl.ObstacleType;
import it.unibo.elementsduo.model.obstacles.staticobstacles.solid.Floor;
import it.unibo.elementsduo.model.obstacles.staticobstacles.solid.Wall;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Tests for obstacle factories.
 */
final class TestObstacleFactories {

    private static final double WIDTH = 2.0;
    private static final double HEIGHT = 1.0;

    private ObstacleFactory staticFactory;
    private InteractiveObstacleFactoryImpl interactiveFactory;

    @BeforeEach
    void setUp() {
        this.staticFactory = new ObstacleFactoryImpl();
        this.interactiveFactory = new InteractiveObstacleFactoryImpl();
    }

    @Test
    void createsHazardsWithTypes() {
        final var hitBox = new HitBoxImpl(new Position(0, 0), HEIGHT, WIDTH);

        final Hazard water = (Hazard) staticFactory.createObstacle(ObstacleType.WATER_POOL, hitBox);
        final Hazard lava = (Hazard) staticFactory.createObstacle(ObstacleType.LAVA_POOL, hitBox);
        final Hazard poison = (Hazard) staticFactory.createObstacle(ObstacleType.GREEN_POOL, hitBox);

        assertEquals(HazardType.WATER, water.getHazardType());
        assertEquals(HazardType.LAVA, lava.getHazardType());
        assertEquals(HazardType.POISON, poison.getHazardType());

        assertEquals(CollisionLayer.HAZARD, water.getCollisionLayer());
        assertEquals(CollisionLayer.HAZARD, lava.getCollisionLayer());
        assertEquals(CollisionLayer.HAZARD, poison.getCollisionLayer());
    }

    @Test
    void createsSolidWallsAndFloors() {
        final var hitBox = new HitBoxImpl(new Position(1, 2), HEIGHT, WIDTH);

        final AbstractStaticObstacle wall = staticFactory.createObstacle(ObstacleType.WALL, hitBox);
        final AbstractStaticObstacle floor = staticFactory.createObstacle(ObstacleType.FLOOR, hitBox);

        assertTrue(wall instanceof Wall);
        assertTrue(floor instanceof Floor);
        assertEquals(CollisionLayer.STATIC_OBSTACLE, wall.getCollisionLayer());
        assertEquals(CollisionLayer.STATIC_OBSTACLE, floor.getCollisionLayer());
    }

    @Test
    void interactiveFactoryCreatesExpectedInstances() {
        final Position center = new Position(0, 0);
        assertNotNull(interactiveFactory.createLever(center));
        assertNotNull(interactiveFactory.createButton(center));

        final PushBox pushBox = interactiveFactory.createPushBox(center);
        assertNotNull(pushBox);
        assertEquals(CollisionLayer.PUSHABLE, pushBox.getCollisionLayer());
        assertEquals(Vector2D.ZERO, pushBox.getVelocity());
        assertFalse(pushBox.isOnGround());

        final Position pointA = new Position(-1, 0);
        final Position pointB = new Position(1, 0);
        final PlatformImpl platform = interactiveFactory.createMovingPlatform(center, pointA, pointB);
        assertNotNull(platform);
        assertEquals(CollisionLayer.PLATFORM, platform.getCollisionLayer());
        assertEquals(Vector2D.ZERO, platform.getVelocity());
    }
}
