package it.unibo.elementsduo.model.player.api.handlers;

import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles player input events and translates them into in-game actions.
 */
@FunctionalInterface
public interface PlayerInputHandler extends PlayerHandler {

    /**
     * Processes the player's input and updates its state accordingly.
     *
     * @param player the player whose input is being handled
     *
     * @param inputController the input controller providing the current key states
     */
    void handleInput(Player player, InputController inputController);
}
