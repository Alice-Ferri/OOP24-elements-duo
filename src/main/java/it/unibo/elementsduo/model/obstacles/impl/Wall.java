package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public class Wall implements Collidable {
    HitBox hitBox;

    public Wall(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }
}
