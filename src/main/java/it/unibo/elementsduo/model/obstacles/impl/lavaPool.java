package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public class lavaPool implements Collidable {

    HitBox hitbox;

    public lavaPool(HitBox hitBox) {
        this.hitbox = hitbox;
    }

    @Override
    public HitBox getHitBox() {
        return hitbox;
    }

}