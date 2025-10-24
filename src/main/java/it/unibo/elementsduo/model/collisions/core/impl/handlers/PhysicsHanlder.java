package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.resources.Vector2D;

public class PhysicsHanlder implements CollisionHandler {

    CollisionInformationsImpl c;
    Movable entity;

    @Override
    public void handle(CollisionInformations c) {
        Movable movable = null;
        Vector2D normal = c.getNormal();
        if (c.getObjectA() instanceof Movable m)
            movable = m;
        else if (c.getObjectB() instanceof Movable m) {
            movable = m;
            normal.multiply(-1);
        }

        if (movable != null) {
            movable.correctPhysicsCollision(c.getPenetration(), normal);
        }
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return a instanceof Movable || b instanceof Movable;
    }

}
