package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public class waterPool extends Wall {

    HitBox hitbox;

    public waterPool(HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

}