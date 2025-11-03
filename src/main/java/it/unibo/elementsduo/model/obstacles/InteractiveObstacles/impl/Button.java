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
<<<<<<< HEAD
 * <p>
 * When pressed or released, it notifies all linked {@link TriggerListener}
 * objects of its state change.
 * </p>
=======
 * When pressed or released, it notifies all registered {@link TriggerListener}s
 * about its new state.
 * <p>
 * This class is not designed for extension and is therefore declared as final.
>>>>>>> powerups
 */
public final class Button extends AbstractInteractiveObstacle implements TriggerSource, Pressable {

    private static final double HALF_WIDTH = 0.5;
    private static final double HALF_HEIGHT = 0.5;

    private boolean active;

    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
<<<<<<< HEAD
     * Creates a new button centered at the specified position.
     *
     * @param center the position of the button's center
=======
     * Creates a button centered at the specified position.
     *
     * @param center the position of the button
>>>>>>> powerups
     */
    public Button(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     *
     * @return true if the button is currently active (pressed), false otherwise
>>>>>>> powerups
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     * <p>
     * Activates the button and notifies all registered listeners if it was not
     * already active.
>>>>>>> powerups
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
<<<<<<< HEAD
=======
     * <p>
     * Deactivates the button and notifies all registered listeners if it was
     * active.
>>>>>>> powerups
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
<<<<<<< HEAD
=======
     * <p>
     * Adds a {@link TriggerListener} to be notified when the button is pressed or
     * released.
     *
     * @param listener the listener to add
>>>>>>> powerups
     */
    @Override
    public void addListener(final TriggerListener listener) {
        this.linkedObjects.add(listener);
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     * <p>
     * Removes a {@link TriggerListener} from the list of listeners.
     *
     * @param listener the listener to remove
>>>>>>> powerups
     */
    @Override
    public void removeListener(final TriggerListener listener) {
        this.linkedObjects.remove(listener);
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     *
     * @return the collision layer associated with this button
>>>>>>> powerups
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.BUTTON;
    }
}
