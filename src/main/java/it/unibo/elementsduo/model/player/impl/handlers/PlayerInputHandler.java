package it.unibo.elementsduo.model.player.impl.handlers;

import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.controller.inputcontroller.impl.InputState;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

/**
 * Handles player input for movement and jumping.
 */
public class PlayerInputHandler {

    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;

    private final PlayerPhysicsHandler physicsHandler;

    /**
     * Creates a new PlayerInputHandler.
     *
     * @param physicsHandler the physics handler for player actions
     */
    public PlayerInputHandler(final PlayerPhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    /**
     * Handles the input player.
     *
     * @param player to handle input
     *
     * @param inputController gets the input
     */
    public void handleInput(final Player player, final InputController inputController) {
        final PlayerType type = player.getPlayerType();

        final InputState state = inputController.getInputState();

        final boolean left = state.isActionPressed(type, InputState.Action.LEFT);
        final boolean right = state.isActionPressed(type, InputState.Action.RIGHT);

        if (left == right) {
            player.setVelocityX(0);
        } else if (left) {
            player.setVelocityX(-RUN_SPEED);
        } else {
            player.setVelocityX(RUN_SPEED);
        }

        if (state.isActionPressed(type, InputState.Action.JUMP)) {
            physicsHandler.jump(player, JUMP_STRENGTH);
            inputController.markJumpHandled(type);
        }
    }
}
