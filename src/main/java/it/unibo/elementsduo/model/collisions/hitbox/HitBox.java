package it.unibo.elementsduo.model.collisions.hitbox;

import it.unibo.elementsduo.resources.Position;

public interface HitBox {
    Position getCenter();

    boolean collision(HitBox hitbox);
}
