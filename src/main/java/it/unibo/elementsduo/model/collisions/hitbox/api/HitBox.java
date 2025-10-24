package it.unibo.elementsduo.model.collisions.hitbox.api;

import it.unibo.elementsduo.resources.Position;

public interface HitBox {
    Position getCenter();

    double getHalfHeight();

    double getHalfWidth();

    boolean intersects(HitBox hitbox);
}
