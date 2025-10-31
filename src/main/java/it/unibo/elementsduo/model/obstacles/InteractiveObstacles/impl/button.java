package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;

import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents a push button in the game world that can activate or deactivate
 * linked {@link TriggerListener} objects when pressed or released.
 * <p>
 * The button maintains an internal active state and notifies all linked
 * listeners whenever that state changes.
 */
public class button extends InteractiveObstacle implements Triggerable {

    private boolean active = false;
    private ArrayList<TriggerListener> linkedObjects = new ArrayList<>();
    private final static double halfWidth = 0.5;
    private final static double halfHeight = 0.5;

    /**
     * Creates a new button centered at the given position.
     *
     * @param center the position of the button's center
     */
    public button(Position center) {
        super(center, halfWidth, halfHeight);
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
     * <p>
     * When activated, the button notifies all linked {@link TriggerListener}
     * objects with {@code true}.
     */
    @Override
    public void activate() {
        if (!this.active) {
            this.active = true;
            this.linkedObjects.stream().forEach(t -> t.onTriggered(active));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * When deactivated, the button notifies all linked {@link TriggerListener}
     * objects with {@code false}.
     */
    @Override
    public void deactivate() {
        if (this.active) {
            this.active = false;
            this.linkedObjects.stream().forEach(t -> t.onTriggered(active));
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * The button does not support toggling directly and will throw an
     * {@link UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException always thrown
     */
    @Override
    public void toggle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggle'");
    }

    /**
     * Links a {@link TriggerListener} to this button.
     * <p>
     * When the button is activated or deactivated, the listener is notified
     * with the new state.
     *
     * @param t the listener to link
     */
    public void linkTo(TriggerListener t) {
        linkedObjects.add(t);
    }

}
