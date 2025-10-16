package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.obstacle;

public class Wall implements obstacle {
    HitBox hitBox;

    public Wall(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }
}
