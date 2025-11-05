package it.unibo.elementsduo.model.player.api.handlers;

import it.unibo.elementsduo.model.player.api.Player;

/**
 * Handles physics updates for the player, such as gravity and movement.
 */
public interface PlayerPhysicsHandler extends PlayerHandler {

    /**
     * Updates the player's position based on velocity and elapsed time.
     *
     * @param player the player to update
     *
     * @param deltaTime the time elapsed since the last update
     */
    void updatePosition(Player player, double deltaTime);

    /**
     * Permits to apply the jump to the player.
     *
     * @param player to set jumping
     *
     * @param strength to jump
     */
    void jump(Player player, double strength);
}
