package it.unibo.elementsduo.model.collisions.core.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.api.Movable;

public class CollisionHandlerImpl implements CollisionHandler {

    CollisionInformationsImpl c;
    Movable entity;

    @Override
    public void handle(CollisionInformations c) {
        if (c.getObjectA() instanceof Movable)
            this.entity = (Movable) c.getObjectA();
        else if (c.getObjectB() instanceof Movable)
            this.entity = (Movable) c.getObjectB();
        this.entity.correctPhysicsCollision(c.getPenetration(), c.getNormal());
    }

}
