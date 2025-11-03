package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.resources.Vector2D;

public final class PhysicsHandler extends AbstractCollisionHandler<Movable, Collidable> {

    public PhysicsHandler() {
        super(Movable.class, Collidable.class);
    }

    @Override
    protected void handleCollision(final Movable movable, final Collidable other,
            final CollisionInformations c, final CollisionResponse.Builder builder) {

        final Vector2D normal = getNormalFromPerspective(movable, c);

        if (other instanceof Movable otherMovable && otherMovable.resolvePhysicsWith(movable)) {
            builder.addPhysicsCommand(
                    () -> resolveMovablesCollision(movable, otherMovable, normal, c.getPenetration()));
        } else {
            builder.addPhysicsCommand(() -> movable.correctPhysicsCollision(c.getPenetration(), normal, other));
        }
    }

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