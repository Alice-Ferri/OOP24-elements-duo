package it.unibo.elementsduo.model.collisions.core.api;

import java.util.EnumSet;

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
    default boolean hasPhysicsResponse() {
        return true;
    }

    // my layer so other can know if colliding with me
    CollisionLayer getCollisionLayer();

    // objects i want to collide
    default EnumSet<CollisionLayer> getCollisionMask() {
        return this.getCollisionLayer().getDefaultMask();
    }

    default boolean resolvePhysicsWith(final Collidable other) {
        if (!this.hasPhysicsResponse() || !other.hasPhysicsResponse()) {
            return false;
        }
        return this.getCollisionMask().contains(other.getCollisionLayer())
                || other.getCollisionMask().contains(this.getCollisionLayer());
    }
}
