package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Pressable;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a button that can be pressed and released.
 * <p>
 * When pressed or released, it notifies all linked {@link TriggerListener}
 * objects of its state change.
 * </p>
 */
public final class Button extends AbstractInteractiveObstacle implements TriggerSource, Pressable {

    private static final double HALF_WIDTH = 0.5;

    private static final double HALF_HEIGHT = 0.5;

    private boolean active;

    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
     * Creates a new button centered at the specified position.
     *
     * @param center the position of the button's center
     */
    public Button(final Position center) {
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
    public void press() {
        if (!this.active) {
            this.active = true;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void release() {
        if (this.active) {
            this.active = false;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
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
        return CollisionLayer.BUTTON;
    }
}
