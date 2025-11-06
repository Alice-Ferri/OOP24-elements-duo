package it.unibo.elementsduo.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.interactiveobstacles.impl.PlatformImpl;
import it.unibo.elementsduo.resources.Position;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Tests for {@link PlatformImpl}.
 */
final class TestPlatformImpl {

    private static final double EPSILON = 1e-6;
    private static final double DELTA = 0.5;
    private static final double SPEED = 1.0;

    private PlatformImpl platform;
    private Position start;
    private Position pointA;
    private Position pointB;

    @BeforeEach
    void setUp() {
        this.start = new Position(0, 0);
        this.pointA = new Position(-1, 0);
        this.pointB = new Position(1, 0);
        this.platform = new PlatformImpl(start, pointA, pointB);
    }

    @Test
    void startsWithZeroVelocity() {
        assertFalse(platform.isActive());
        assertEquals(Vector2D.ZERO, platform.getVelocity());
        assertEquals(CollisionLayer.PLATFORM, platform.getCollisionLayer());
    }

    @Test
    void activatesTowardsPointB() {
        platform.activate();
        platform.update(DELTA);

        assertTrue(platform.isActive());
        assertEquals(SPEED, platform.getVelocity().x(), EPSILON);
        assertEquals(DELTA, platform.getCenter().x(), EPSILON);
    }

    @Test
    void reversesDirection() {
        platform.activate();
        platform.update(1.8);
        final double previousX = platform.getCenter().x();

        // next it flip direction and moves towards A
        platform.update(0.5);

        assertTrue(platform.getVelocity().x() <= 0);
        assertTrue(platform.getCenter().x() < previousX);
    }

    @Test
    void deactivateStopsMovement() {
        platform.activate();
        platform.update(DELTA);
        platform.deactivate();
        assertFalse(platform.isActive());

        platform.update(DELTA);
        assertEquals(Vector2D.ZERO, platform.getVelocity());
        assertEquals(DELTA, platform.getCenter().x(), EPSILON, "Position stays where it was when deactivated");
    }

    @Test
    void toggleSwitchesStateAndResetsVelocity() {
        platform.toggle();
        assertTrue(platform.isActive());

        platform.update(DELTA);
        platform.toggle();
        assertFalse(platform.isActive());
        assertEquals(Vector2D.ZERO, platform.getVelocity());
    }

    @Test
    void triggerListenerCallsActivateDeactivate() {
        platform.onTriggered(true);
        assertTrue(platform.isActive());

        platform.onTriggered(false);
        assertFalse(platform.isActive());
    }
}
