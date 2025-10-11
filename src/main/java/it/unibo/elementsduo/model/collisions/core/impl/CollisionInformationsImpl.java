package it.unibo.elementsduo.model.collisions.core.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.resources.Vector2D;

public class CollisionInformationsImpl implements CollisionInformations {

    Collidable objectA;
    Collidable objectB;
    double penetration;
    Vector2D normal;

    public CollisionInformationsImpl(Collidable objectA, Collidable objectB, double penetration, Vector2D normal) {
        this.objectA = objectA;
        this.objectB = objectB;
        this.penetration = penetration;
        this.normal = normal;
    }

    @Override
    public Collidable getObjectA() {
        return this.objectA;
    }

    @Override
    public Collidable getObjectB() {
        return this.objectB;
    }

    @Override
    public double getPenetration() {
        return this.penetration;
    }

    @Override
    public Vector2D getNormal() {
        return this.normal;
    }

}
