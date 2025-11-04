package it.unibo.elementsduo.controller.inputcontroller.api;

import it.unibo.elementsduo.controller.inputcontroller.impl.InputState;
import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Public interface for the input controller.
 */
public interface InputController {

    /**
     * Installs the keyboard listener.
     */
    void install();

    /**
     * Uninstalls the keyboard listener and clears internal state.
     */
    void uninstall();

    /**
     * Enables or disables the controller.
     *
     * @param enabled true to enable, false to disable
     */
    void setEnabled(boolean enabled);

    /**
     * Checks if the controller is enabled.
     *
     * @return true if enabled
     */
    boolean isEnabled();

    /**
     * Returns the current snapshot of all player input states.
     *
     * @return an input state representing the current pressed actions for each player
     */
    InputState getInputState();

    /**
     * Marks the jump action for the given player as handled.
     *
     * @param type the player type whose jump input
     */
    void markJumpHandled(PlayerType type);

}
