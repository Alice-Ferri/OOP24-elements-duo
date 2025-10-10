package it.unibo.elementsduo.model.collisions.core;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

/*interface implemented by all objects with a physicality */

public interface Collidable {
    HitBox getHitBox();
}
