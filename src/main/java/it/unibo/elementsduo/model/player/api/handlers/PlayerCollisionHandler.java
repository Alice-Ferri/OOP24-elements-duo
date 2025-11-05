package it.unibo.elementsduo.model.player.api.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles player collision resolution with other game objects.
 */
@FunctionalInterface
public interface PlayerCollisionHandler extends PlayerHandler {

    /**
     * Manages collision response between the player and another object.
     * 
     * @param player the player to correct the collision
     *
     * @param penetration the depth of the overlap
     *
     * @param normal the collision normal vector
     *
     * @param other the collidable object involved in the collision
     */
    void handleCollision(Player player, double penetration, Vector2D normal, Collidable other);
}
