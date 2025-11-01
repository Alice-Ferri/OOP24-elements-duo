package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a push button in the game world that can activate or deactivate
 * linked {@link TriggerListener} objects when pressed or released.
 *
 * <p>
 * The button maintains an internal active state and notifies all linked
 * listeners whenever that state changes.
 * </p>
 */
public class button extends AbstractInteractiveObstacle implements Triggerable {

    /** The button's half width. */
    private static final double HALF_WIDTH = 0.5;

    /** The button's half height. */
    private static final double HALF_HEIGHT = 0.5;

    /** Indicates whether the button is currently active. */
    private boolean active;

    /** The list of linked trigger listeners. */
    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
     * Creates a new button centered at the given position.
     *
     * @param center the position of the button's center
     */
    public button(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * When activated, the button notifies all linked {@link TriggerListener}
     * objects with {@code true}.
     * </p>
     */
    @Override
    public void activate() {
        if (!this.active) {
            this.active = true;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * When deactivated, the button notifies all linked {@link TriggerListener}
     * objects with {@code false}.
     * </p>
     */
    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
            this.linkedObjects.forEach(t -> t.onTriggered(this.active));
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The button does not support toggling directly and will throw an
     * {@link UnsupportedOperationException}.
     * </p>
     *
     * @throws UnsupportedOperationException always thrown
     */
    @Override
    public void toggle() {
        throw new UnsupportedOperationException("Button does not support toggling directly.");
    }

    /**
     * Links a {@link TriggerListener} to this button.
     *
     * <p>
     * When the button is activated or deactivated, the listener is notified
     * with the new state.
     * </p>
     *
     * @param listener the listener to link
     */
    public void linkTo(final TriggerListener listener) {
        this.linkedObjects.add(listener);
    }
}
