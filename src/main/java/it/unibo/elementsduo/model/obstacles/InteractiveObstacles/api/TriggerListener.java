package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

/**
 * Listener interface for receiving notifications when a {@link Triggerable}
 * object changes its state.
 * 
 * Implementing classes define how to react when a trigger is activated or
 * deactivated in the game world.
 */
public interface TriggerListener {
    /**
     * Called when the associated trigger changes its state.
     *
     * @param m {@code true} if the trigger has been activated, {@code false} if it
     *          has been deactivated
     */
    void onTriggered(boolean state);
}
