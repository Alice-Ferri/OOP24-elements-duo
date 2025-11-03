package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Represents a collision command that corrects the position and velocity
 * of a movable object after a physics collision.
 */
public final class PhysicsCorrectionCommand implements CollisionCommand {

    private final Movable movable;
    private final Collidable other;
    private final double penetration;
    private final Vector2D normal;

    /**
     * Creates a new {@code PhysicsCorrectionCommand}.
     *
     * @param movable     the movable object to correct
     * @param other       the collidable object involved in the collision
     * @param penetration the depth of penetration between the objects
     * @param normal      the collision normal vector
     */
    public PhysicsCorrectionCommand(final Movable movable, final Collidable other, final double penetration,
            final Vector2D normal) {
        this.movable = movable;
        this.penetration = penetration;
        this.normal = normal;
        this.other = other;
    }

    /**
     * Executes the physics correction by invoking the movable object's
     * {@link Movable#correctPhysicsCollision(double, Vector2D, Collidable)} method.
     */
    @Override
    public void execute() {
        this.movable.correctPhysicsCollision(this.penetration, this.normal, this.other);
    }
}
