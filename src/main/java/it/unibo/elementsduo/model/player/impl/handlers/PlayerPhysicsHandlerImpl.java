package it.unibo.elementsduo.model.player.impl.handlers;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.handlers.PlayerPhysicsHandler;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Class to handle the player physics.
 */
public class PlayerPhysicsHandlerImpl implements PlayerPhysicsHandler {

    private static final double GRAVITY = 9.8;

    /**
     * Updates the player position.
     *
     * @param player to update
     *
     * @param deltaTime from last update
     */
    @Override
    public void updatePosition(final Player player, final double deltaTime) {
        this.applyGravity(player, deltaTime);
        final Vector2D velocity = player.getVelocity();
        player.correctPosition(velocity.x() * deltaTime, velocity.y() * deltaTime);
    }

    /**
     * Jumps the player if is on ground.
     *
     * @param player to jump
     *
     * @param jumpStrength jump power
     */
    public void jump(final Player player, final double jumpStrength) {
        if (player.isOnGround()) {
            final Vector2D velocity = player.getVelocity();
            player.setVelocityY(velocity.y() - jumpStrength);
            player.setAirborne();
        }
    }

    /**
     * Apply the gravity if is not on ground.
     *
     * @param player to update
     *
     * @param deltaTime from last update
     */
    private void applyGravity(final Player player, final double deltaTime) {
        if (!player.isOnGround()) {
            final Vector2D velocity = player.getVelocity();
            player.setVelocityY(velocity.y() + GRAVITY * deltaTime);
        }
    }
}
