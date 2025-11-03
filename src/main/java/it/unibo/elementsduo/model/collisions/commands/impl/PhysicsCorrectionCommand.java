package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * A command that applies a physics-based collision correction between two
 * objects.
 * <p>
 * This class encapsulates all necessary data to perform a physics correction
 * (penetration depth, collision normal, and the involved objects).
 * </p>
 * <p>
 * This class is not designed for extension and is declared as final.
 * </p>
 */
public final class PhysicsCorrectionCommand implements CollisionCommand {

    /** The movable object affected by the collision. */
    private final Movable movable;

    /** The other collidable object involved in the collision. */
    private final Collidable other;

    /** The penetration depth of the collision. */
    private final double penetration;

    /** The normal vector of the collision. */
    private final Vector2D normal;

    /**
     * Creates a new {@code PhysicsCorrectionCommand}.
     *
     * @param movable     the movable object affected by the collision
     * @param other       the other collidable object involved
     * @param penetration the penetration depth
     * @param normal      the collision normal vector
     */
    public PhysicsCorrectionCommand(final Movable movable, final Collidable other,
            final double penetration, final Vector2D normal) {
        this.movable = movable;
        this.other = other;
        this.penetration = penetration;
        this.normal = normal;
    }

    /**
     * Executes the physics correction by applying the penetration and normal
     * to the movable object.
     */
    @Override
    public void execute() {
        this.movable.correctPhysicsCollision(this.penetration, this.normal, this.other);
    }

    /**
     * Returns the movable object associated with this command.
     *
     * @return the movable object
     */
    public Movable getMovable() {
        return this.movable;
    }

    /**
     * Returns the other collidable object involved in the collision.
     *
     * @return the other collidable
     */
    public Collidable getOther() {
        return this.other;
    }

    /**
     * Returns the penetration depth of the collision.
     *
     * @return the penetration depth
     */
    public double getPenetration() {
        return this.penetration;
    }

    /**
     * Returns the normal vector of the collision.
     *
     * @return the collision normal
     */
    public Vector2D getNormal() {
        return this.normal;
    }
}
