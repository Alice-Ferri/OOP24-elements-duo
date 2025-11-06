package it.unibo.elementsduo.model.obstacles.interactiveobstacles.api;

/**
 * A functional interface for objects that can listen to trigger events.
 * Implementors define what action to take when a trigger source
 * (like a button or lever) changes its state.
 */
@FunctionalInterface
public interface TriggerListener {

    /**
     * Called when the associated trigger changes its state.
     *
     * @param state {@code true} if the trigger has been activated, {@code false} if
     *              it
     *              has been deactivated
     */
    void onTriggered(boolean state);
}
