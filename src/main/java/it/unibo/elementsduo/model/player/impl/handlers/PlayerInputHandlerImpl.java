package it.unibo.elementsduo.model.player.impl.handlers;

import java.util.Optional;

import it.unibo.elementsduo.controller.inputcontroller.api.InputController;
import it.unibo.elementsduo.controller.inputcontroller.impl.InputState;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.api.handlers.PlayerInputHandler;
import it.unibo.elementsduo.model.player.api.handlers.PlayerPhysicsHandler;

/**
 * Handles player input for movement and jumping.
 */
public class PlayerInputHandlerImpl implements PlayerInputHandler {

    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;

    private final PlayerPhysicsHandler physicsHandler;

    /**
     * Creates a new PlayerInputHandler.
     *
     * @param physicsHandler the physics handler for player actions
     */
    public PlayerInputHandlerImpl(final PlayerPhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    /**
     * Handles the input player.
     *
     * @param player to handle input
     *
     * @param inputController gets the input
     */
    @Override
    public void handleInput(final Player player, final InputController inputController) {
        final PlayerType type = player.getPlayerType();
        final InputState state = inputController.getInputState();
    
        final boolean left = state.isActionPressed(type, InputState.Action.LEFT);
        final boolean right = state.isActionPressed(type, InputState.Action.RIGHT);
    
        Optional.of((right ? 1 : 0) - (left ? 1 : 0))
                .ifPresent(direction -> player.setVelocityX(direction * RUN_SPEED));
    
        Optional.of(state)
                .filter(s -> s.isActionPressed(type, InputState.Action.JUMP))
                .ifPresent(s -> {
                    physicsHandler.jump(player, JUMP_STRENGTH);
                    inputController.markJumpHandled(type);
                });
    }
}
