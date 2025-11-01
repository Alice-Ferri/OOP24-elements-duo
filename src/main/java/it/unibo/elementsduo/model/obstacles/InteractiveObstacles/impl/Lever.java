package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a lever in the game world that can be toggled on or off.
 *
 * <p>
 * A lever maintains an internal active state and can notify all linked
 * {@link TriggerListener} objects whenever its state changes.
 * </p>
 */
public class Lever extends AbstractInteractiveObstacle implements Triggerable {

    /** The lever's half width. */
    private static final double HALF_WIDTH = 0.5;

    /** The lever's half height. */
    private static final double HALF_HEIGHT = 0.5;

    /** Whether the lever is currently active. */
    private boolean active;

    /** The list of linked {@link TriggerListener} objects. */
    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
     * Creates a new lever centered at the given position.
     *
     * @param center the position of the lever's center
     */
    public Lever(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /** {@inheritDoc} */
    @Override
    public void activate() {
        if (!this.active) {
            this.active = true;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * When toggled, the lever switches its state and notifies all linked
     * {@link TriggerListener} objects with the new active state.
     * </p>
     */
    @Override
    public void toggle() {
        this.active = !this.active;
        this.linkedObjects.forEach(t -> t.onTriggered(active));
    }

    /**
     * Indicates that the lever is not a solid obstacle.
     *
     * @return {@code false}, since levers can be passed through
     */
    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }

    /**
     * Links a {@link TriggerListener} to this lever.
     *
     * <p>
     * When the lever is toggled, the listener will be notified of the new state.
     * </p>
     *
     * @param t the listener to link to this lever
     */
    public void addLinkedObject(final TriggerListener t) {
        linkedObjects.add(t);
    }
}
