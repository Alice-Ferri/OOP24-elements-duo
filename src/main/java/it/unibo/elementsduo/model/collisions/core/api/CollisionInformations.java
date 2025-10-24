package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.resources.Vector2D;

public interface CollisionInformations {

    Collidable getObjectA();

    Collidable getObjectB();

    double getPenetration();

    Vector2D getNormal();
}
