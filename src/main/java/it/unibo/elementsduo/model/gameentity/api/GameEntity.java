package it.unibo.elementsduo.model.gameentity.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

/**
 * Represents a generic entity within the game.
 * This is a marker interface for all objects that exist within the game world,
 * such as players, enemies, and obstacles.
 */
@FunctionalInterface
public interface GameEntity {
    /**
     * Returns the {@link HitBox} that defines the physical bounds of this
     * collidable object.
     *
     * @return the hitbox representing this object's collision area
     */
    HitBox getHitBox();

}
