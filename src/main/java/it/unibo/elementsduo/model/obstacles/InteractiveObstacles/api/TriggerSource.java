package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents a source of triggers that can notify registered listeners
 * when specific conditions are met.
 * This interface extends {@link Collidable}, allowing it to participate
 * in collision detection.
 */
public interface TriggerSource extends Collidable {

    /**
     * Adds a listener to be notified of trigger events.
     *
     * @param listener the listener to add
     */
    void addListener(TriggerListener listener);

    /**
     * Removes a previously registered listener.
     *
     * @param listener the listener to remove
     */
    void removeListener(TriggerListener listener);

    /**
     * Checks whether the trigger source is currently active.
     *
     * @return true if the trigger is active, false otherwise
     */
    boolean isActive();
}
