package it.unibo.elementsduo.controller.inputController.api;

import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Public interface for the input controller.
 */
public interface InputController {

    /**
     * Checks if the move-left key is pressed.
     *
     * @param type the player type
     * @return true if the move-left key is pressed
     */
    boolean isMoveLeftPressed(PlayerType type);

    /**
     * Checks if the move-right key is pressed.
     *
     * @param type the player type
     * @return true if the move-right key is pressed
     */
    boolean isMoveRightPressed(PlayerType type);

    /**
     * Checks if the jump key is pressed.
     *
     * @param type the player type
     * @return true if the jump key is pressed
     */
    boolean isJumpPressed(PlayerType type);

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
}
