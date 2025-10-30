package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PhysicsCorrectionCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.enemies.impl.ShooterEnemyImpl;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PhysicsHandler implements CollisionHandler {

    CollisionInformationsImpl c;
    Movable entity;

    @Override
    public void handle(CollisionInformations c, CollisionResponse collisionResponse) {
        final Collidable objectA = c.getObjectA();
        final Collidable objectB = c.getObjectB();

        if (objectA instanceof ShooterEnemyImpl && objectB instanceof Projectiles ||
                objectA instanceof Projectiles && objectB instanceof ShooterEnemyImpl) {
            return;
        }

        if (objectA instanceof Projectiles && objectB instanceof Player ||
                objectA instanceof Player && objectB instanceof Projectiles) {
            return;
        }

        final boolean a = objectA instanceof Movable;
        final boolean b = objectB instanceof Movable;

        if (!a && !b) {
            return;
        }

        final Vector2D normal = c.getNormal();
        final double penetration = c.getPenetration();

        if (a) {
            final double correction = b ? penetration / 2.0 : penetration;
            collisionResponse.addPhysicsCommand(
                    new PhysicsCorrectionCommand((Movable) objectA, objectB, correction, normal));
        }

        if (b) {
            final double correction = a ? penetration / 2.0 : penetration;
            collisionResponse.addPhysicsCommand(
                    new PhysicsCorrectionCommand((Movable) objectB, objectA, correction, normal.multiply(-1)));
        }
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        if (!a.isSolid() || !b.isSolid())
            return false;
        return a instanceof Movable || b instanceof Movable;
    }

}
