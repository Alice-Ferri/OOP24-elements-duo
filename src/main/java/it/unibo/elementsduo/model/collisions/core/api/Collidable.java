package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

/**
 * Represents any object in the game world that can participate in collisions.
 * 
 * <p>
 * All physical entities such as players, enemies, and obstacles implement this
 * interface.
 */
public interface Collidable {

    /**
     * Returns the {@link HitBox} that defines the physical bounds of this
     * collidable object.
     *
     * @return the hitbox representing this object's collision area
     */
    HitBox getHitBox();

    /**
     * Indicates whether this object is solid, meaning it should block or
     * interact physically with other solid objects.
     *
     * @return {@code true} if the object is solid, {@code false} otherwise
     */
    default boolean isSolid() {
        return true;
    }
}
