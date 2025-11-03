package it.unibo.elementsduo.model.collisions.core.api;

import java.util.EnumSet;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

/**
 * Represents any object in the game world that can participate in collisions.
 * 
 * <p>
 * All physical entities such as players, enemies, and obstacles implement this
 * interface.
 * </p>
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
     * Returns the {@link CollisionLayer} associated with this object.
     * <p>
     * The collision layer determines what types of entities this object can
     * interact with during collision checks.
     * </p>
     *
     * @return the collision layer of this object
     */
    CollisionLayer getCollisionLayer();

    /**
     * Returns the set of {@link CollisionLayer}s that this object is allowed to
     * collide with.
     *
     * @return an {@link EnumSet} of layers representing allowed collisions
     */
    default EnumSet<CollisionLayer> getCollisionMask() {
        return this.getCollisionLayer().getDefaultMask();
    }

    /**
     * Determines whether this object should resolve a physics interaction with
     * another {@link Collidable}.
     *
     * <p>
     * This check ensures that both objects have physical responses enabled and
     * that at least one of their collision masks allows interaction with the
     * other.
     * </p>
     *
     * @param other the other {@link Collidable} to check collision compatibility
     *              with
     * @return {@code true} if the objects should resolve a physics collision,
     *         {@code false} otherwise
     */
    default boolean resolvePhysicsWith(final Collidable other) {
        if (!this.hasPhysicsResponse() || !other.hasPhysicsResponse()) {
            return false;
        }
        return this.getCollisionMask().contains(other.getCollisionLayer())
                || other.getCollisionMask().contains(this.getCollisionLayer());
    }
}
