package it.unibo.elementsduo.controller.inputController.api;

import it.unibo.elementsduo.controller.inputController.impl.InputState;
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

    InputState getInputState();

    void markJumpHandled(PlayerType type);
}
