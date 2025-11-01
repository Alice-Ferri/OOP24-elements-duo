package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Vector2D;

public class PlayerInputHandler {
    private static final double RUN_SPEED = 8.0;
    private static final double JUMP_STRENGTH = 6.0;

    public Vector2D handleInput(final InputController input, fianl PlayerType type, final boolean onGround) {
        double vx = 0, vy = 0;

        boolean left = input.isMoveLeftPressed(type);
        boolean right = input.isMoveRightPressed(type);

        if (left == right) {
            vx = 0;
        } else if (left) {
            vx = -RUN_SPEED;
        } else {
            vx = RUN_SPEED;
        }

        if (input.isJumpPressed(type) && onGround) {
            vy = -JUMP_STRENGTH;
        }

        return new Vector2D(vx, vy);
    }
}