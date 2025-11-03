package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Toggler;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a lever that can be toggled on and off.
 * <p>
 * When toggled, it notifies all linked {@link TriggerListener} objects of its
 * state change.
 * </p>
 */
public final class Lever extends AbstractInteractiveObstacle implements TriggerSource, Toggler {

    private static final double HALF_WIDTH = 0.2;
    private static final double HALF_HEIGHT = 0.5;

    private boolean active;
    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
     * Creates a new lever centered at the specified position.
     *
     * @param center the position of the lever's center
     */
    public Lever(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggle() {
        this.active = !this.active;
        this.linkedObjects.forEach(t -> t.onTriggered(this.active));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(final TriggerListener listener) {
        this.linkedObjects.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(final TriggerListener listener) {
        this.linkedObjects.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.LEVER;
    }
}
