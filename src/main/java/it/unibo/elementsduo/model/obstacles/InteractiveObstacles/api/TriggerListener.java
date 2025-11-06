package it.unibo.elementsduo.model.obstacles.interactiveObstacles.api;

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
