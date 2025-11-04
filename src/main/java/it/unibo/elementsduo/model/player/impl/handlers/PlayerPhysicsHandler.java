package it.unibo.elementsduo.model.player.impl.handlers;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PlayerPhysicsHandler {

    private static final double GRAVITY = 9.8;

    /**
     * Updates the player position
     *
     * @param player to update
     *
     * @param deltaTime from last update
     */
    public void updatePosition(final Player player, final double deltaTime) {
        this.applyGravity(player, deltaTime);
        Vector2D velocity = player.getVelocity();
        player.correctPosition(velocity.x() * deltaTime, velocity.y() * deltaTime);
    }

    /**
     * Fa saltare il player se è a terra
     *
     * @param player il player che salta
     *
     * @param jumpStrength la forza del salto
     */
    public void jump(final Player player, final double jumpStrength) {
        if (player.isOnGround()) {
            Vector2D velocity = player.getVelocity();
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
            Vector2D velocity = player.getVelocity();
            player.setVelocityY(velocity.y() + GRAVITY * deltaTime);
        }
    }
}
