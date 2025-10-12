package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public class waterPool implements Collidable {

    HitBox hitbox;

    public waterPool(HitBox hitBox) {
        this.hitbox = hitbox;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

}