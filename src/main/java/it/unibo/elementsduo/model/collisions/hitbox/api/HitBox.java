package it.unibo.elementsduo.model.collisions.hitbox.api;

import it.unibo.elementsduo.resources.Position;

/**
 * Represents a rectangular hitbox used for collision detection in the game
 * world.
 * 
 * <p>
 * A {@code HitBox} defines the position and size of an object through its
 * center
 * point and half-dimensions.
 */
public interface HitBox {

    /**
     * Returns the center position of this hitbox.
     *
     * @return the center {@link Position} of the hitbox
     */
    Position getCenter();

    /**
     * Returns half of the hitbox's height.
     *
     * @return the half-height of the hitbox
     */
    double getHalfHeight();

    /**
     * Returns half of the hitbox's width.
     *
     * @return the half-width of the hitbox
     */
    double getHalfWidth();

    /**
     * Checks whether this hitbox intersects with another hitbox.
     *
     * @param hitbox the other hitbox to check against
     * @return {@code true} if the two hitboxes intersect, {@code false} otherwise
     */
    boolean intersects(HitBox hitbox);
}
