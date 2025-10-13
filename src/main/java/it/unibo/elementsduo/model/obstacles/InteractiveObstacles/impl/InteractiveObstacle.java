package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public abstract class InteractiveObstacle implements Collidable {

    HitBox hitbox;

    public InteractiveObstacle(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

}
