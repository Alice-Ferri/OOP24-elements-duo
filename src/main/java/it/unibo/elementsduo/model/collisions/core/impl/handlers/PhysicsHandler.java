package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PhysicsCorrectionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Handles physics-based collision resolution between {@link Movable} and
 * {@link Collidable} objects.
 * <p>
 * This handler detects when two physical entities collide and applies
 * corrections to prevent overlapping, either by using a
 * {@link PhysicsCorrectionCommand} or by resolving mutual collisions between
 * two
 * {@link Movable} objects.
 * </p>
 */
public final class PhysicsHandler extends AbstractCollisionHandler<Movable, Collidable> {

    /**
     * Creates a new {@code PhysicsHandler} to manage collisions between movable
     * and collidable objects.
     */
    public PhysicsHandler() {
        super(Movable.class, Collidable.class);
    }

    /**
     * Handles a collision between a {@link Movable} and another
     * {@link Collidable}.
     * <p>
     * If both objects are movable and can resolve physics together, both are
     * corrected symmetrically. Otherwise, a standard
     * {@link PhysicsCorrectionCommand} is issued.
     * </p>
     *
     * @param movable the movable object involved in the collision
     * @param other   the other collidable object involved
     * @param c       the collision information
     * @param builder the builder used to construct the collision response
     */
    @Override
    protected void handleCollision(final Movable movable, final Collidable other,
            final CollisionInformations c, final CollisionResponse.Builder builder) {

        final Vector2D normal = getNormalFromPerspective(movable, c);

        if (other instanceof Movable otherMovable && otherMovable.resolvePhysicsWith(movable)) {
            builder.addPhysicsCommand(
                    () -> resolveMovablesCollision(movable, otherMovable, normal, c.getPenetration()));
        } else {
            builder.addPhysicsCommand(new PhysicsCorrectionCommand(movable, other, c.getPenetration(), normal));
        }
    }

    /**
     * Determines whether this handler can process a given collision between two
     * {@link Collidable} objects.
     * <p>
     * The handler only applies if both objects are physically interactive.
     * </p>
     *
     * @param a the first collidable
     * @param b the second collidable
     * @return {@code true} if the collision can be handled, {@code false} otherwise
     */
    @Override
    public boolean canHandle(final Collidable a, final Collidable b) {

        if (a instanceof Movable && !a.resolvePhysicsWith(b)) {
            return false;
        }

        if (b instanceof Movable && !b.resolvePhysicsWith(a)) {
            return false;
        }

        return super.canHandle(a, b);
    }

    /**
     * Resolves collisions between two {@link Movable} objects symmetrically by
     * applying position corrections and velocity adjustments.
     * <p>
     * The correction depends on the collision direction — vertical or horizontal —
     * to achieve realistic separation and physics response.
     * </p>
     *
     * @param movable       the first movable object
     * @param otherMovable  the second movable object
     * @param movableNormal the collision normal from the first object's perspective
     * @param penetration   the penetration depth between the two objects
     */
    private void resolveMovablesCollision(final Movable movable, final Movable otherMovable,
            final Vector2D movableNormal, final double penetration) {
        final Vector2D otherNormal = movableNormal.multiply(-1);

        if (Math.abs(movableNormal.y()) > Math.abs(movableNormal.x())) {
            if (movableNormal.y() < 0) {
                movable.correctPhysicsCollision(penetration, movableNormal, otherMovable);
            } else if (movableNormal.y() > 0) {
                otherMovable.correctPhysicsCollision(penetration, otherNormal, movable);
            }
        } else {
            final double halfPenetration = penetration / 2.0;
            movable.correctPhysicsCollision(halfPenetration, movableNormal, otherMovable);
            otherMovable.correctPhysicsCollision(halfPenetration, otherNormal, movable);
        }
    }
}
