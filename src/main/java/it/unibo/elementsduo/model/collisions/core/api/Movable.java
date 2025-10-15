package it.unibo.elementsduo.model.collisions.core.api;

import it.unibo.elementsduo.resources.Vector2D;

public interface Movable {
    void correctPhysicsCollision(double penetration, Vector2D normal);
}
