package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.controller.inputController.api.InputController;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles player input logic.
 */
public final class PlayerInputHandler {

    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.5;

    private PlayerInputHandler() { }

    /**
     * Processes player input.
     * 
     * @param input     the input controller
     * @param playerType      the player type
     * @param onGround  whether the player is currently on the ground
     * @return a Vector2D representing the desired velocity direction
     */
    public static Vector2D handleInput(final InputController input, final PlayerType playerType, final boolean onGround) {
        double vx = 0;
        double vy = 0;

        final boolean left = input.isMoveLeftPressed(playerType);
        final boolean right = input.isMoveRightPressed(playerType);

        if (left == right) {
            vx = 0;
        } else if (left) {
            vx = -RUN_SPEED;
        } else {
            vx = RUN_SPEED;
        }

        if (input.isJumpPressed(playerType) && onGround) {
            vy = -JUMP_STRENGTH;
        }

        return new Vector2D(vx, vy);
    }
}
