package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
<<<<<<< HEAD
 * Represents an interactive object that can act as a trigger source,
 * notifying listeners when its state changes.
=======
 * Represents a source of triggers that can notify registered listeners
 * when specific conditions are met.
 * This interface extends {@link Collidable}, allowing it to participate
 * in collision detection.
>>>>>>> powerups
 */
public interface TriggerSource extends Collidable {

    /**
<<<<<<< HEAD
     * Registers a new {@link TriggerListener} to be notified when this
     * trigger source changes state.
=======
     * Adds a listener to be notified of trigger events.
>>>>>>> powerups
     *
     * @param listener the listener to add
     */
    void addListener(TriggerListener listener);

    /**
<<<<<<< HEAD
     * Removes a previously registered {@link TriggerListener}.
=======
     * Removes a previously registered listener.
>>>>>>> powerups
     *
     * @param listener the listener to remove
     */
    void removeListener(TriggerListener listener);

    /**
<<<<<<< HEAD
     * Checks whether this trigger source is currently active.
     *
     * @return {@code true} if active, {@code false} otherwise
=======
     * Checks whether the trigger source is currently active.
     *
     * @return true if the trigger is active, false otherwise
>>>>>>> powerups
     */
    boolean isActive();
}
