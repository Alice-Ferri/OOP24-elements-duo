package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

/**
 * Represents an interactive element that can be activated or deactivated,
 * such as a lever or a button.
 * 
 * <p>
 * Implementations define how the trigger changes state and what effects
 * activation or deactivation produce in the game world.
 */
public interface Triggerable {

    /**
     * Returns whether this trigger is currently active.
     * 
     * <p>
     *
     * @return {@code true} if the trigger is active, {@code false} otherwise
     */
    boolean isActive();

    /**
     * Activates this trigger.
     * 
     * <p>
     * The specific behavior depends on the implementation, such as opening
     * a door or enabling a mechanism.
     */
    void activate();

    /**
     * Deactivates this trigger.
     * 
     * <p>
     * Typically reverses the effect of {@link #activate()}.
     */
    void deactivate();

    /**
     * Toggles the state of this trigger.
     * 
     * <p>
     * If the trigger is active, it becomes inactive; otherwise, it becomes active.
     */
    void toggle();
}
