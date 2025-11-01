package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PhysicsCorrectionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public final class PhysicsHandler extends AbstractCollisionHandler<Movable, Collidable> {

    public PhysicsHandler() {
        super(Movable.class, Collidable.class);
    }

    @Override
    protected void handleCollision(final Movable movable, final Collidable other,
            final CollisionInformations c, final CollisionResponse.Builder builder) {

        final Vector2D normal;
        if (c.getObjectA() == movable) {
            normal = c.getNormal();
        } else {
            normal = c.getNormal().multiply(-1);
        }

        builder.addPhysicsCommand(
                new PhysicsCorrectionCommand(movable, other, c.getPenetration(), normal));
    }

    @Override
    public boolean canHandle(final Collidable a, final Collidable b) {
        if (!a.isSolid() || !b.isSolid()) {
            return false;
        }

        if (a instanceof Movable && b instanceof Movable) {
            return false;
        }

        if (a instanceof ShooterEnemyImpl && b instanceof Projectiles
                || a instanceof Projectiles && b instanceof ShooterEnemyImpl) {
            return false;
        }
        if (a instanceof Projectiles && b instanceof Player
                || a instanceof Player && b instanceof Projectiles) {
            return false;
        }

        return super.canHandle(a, b);
    }
}