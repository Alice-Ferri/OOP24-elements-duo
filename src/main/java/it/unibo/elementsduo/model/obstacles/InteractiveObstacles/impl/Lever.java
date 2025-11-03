package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import java.util.ArrayList;
import java.util.List;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerListener;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.TriggerSource;
import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Toggler;
import it.unibo.elementsduo.resources.Position;

/**
<<<<<<< HEAD
 * Represents a lever that can be toggled on and off.
 * <p>
 * When toggled, it notifies all linked {@link TriggerListener} objects of its
 * state change.
 * </p>
=======
 * Represents a lever that can be toggled between two states (active/inactive).
 * When toggled, it notifies all registered {@link TriggerListener}s.
 * <p>
 * This class is not designed for extension and is declared as final.
>>>>>>> powerups
 */
public final class Lever extends AbstractInteractiveObstacle implements TriggerSource, Toggler {

    private static final double HALF_WIDTH = 0.2;
    private static final double HALF_HEIGHT = 0.5;

    private boolean active;
    private final List<TriggerListener> linkedObjects = new ArrayList<>();

    /**
     * Creates a new lever centered at the specified position.
     *
<<<<<<< HEAD
     * @param center the position of the lever's center
=======
     * @param center the position of the lever
>>>>>>> powerups
     */
    public Lever(final Position center) {
        super(center, HALF_WIDTH, HALF_HEIGHT);
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     *
     * @return true if the lever is currently active, false otherwise
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
     * Toggles the lever state and notifies all registered listeners.
>>>>>>> powerups
     */
    @Override
    public void toggle() {
        this.active = !this.active;
        this.linkedObjects.forEach(t -> t.onTriggered(this.active));
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     *
     * @return false since the lever does not have a physical response
>>>>>>> powerups
     */
    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }

    /**
     * {@inheritDoc}
<<<<<<< HEAD
=======
     * <p>
     * Adds a {@link TriggerListener} that will be notified when the lever is
     * toggled.
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
     * @return the collision layer associated with this lever
>>>>>>> powerups
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.LEVER;
    }
}
