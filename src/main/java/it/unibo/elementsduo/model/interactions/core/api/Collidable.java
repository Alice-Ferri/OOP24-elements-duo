package it.unibo.elementsduo.model.interactions.core.api;

import java.util.Set;

import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;

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

    /**
     * Returns the {@link CollisionLayer} of this object,
     * defining its collision group.
     *
     * @return the collision layer of this object
     */
    CollisionLayer getCollisionLayer();

    /**
     * Returns the set of {@link CollisionLayer}s that this object should
     * check collisions against.
     *
     * @return an {@link Set} representing the collision mask
     */
    default Set<CollisionLayer> getCollisionMask() {
        return this.getCollisionLayer().getDefaultMask();
    }

    /**
     * Determines whether this object should resolve physics interactions
     * with another collidable object.
     *
     * @param other the other collidable object
     * @return {@code true} if physics should be resolved between the two objects
     */
    default boolean resolvePhysicsWith(final Collidable other) {
        if (!this.hasPhysicsResponse() || !other.hasPhysicsResponse()) {
            return false;
        }
        return this.getCollisionMask().contains(other.getCollisionLayer())
                || other.getCollisionMask().contains(this.getCollisionLayer());
    }
}
