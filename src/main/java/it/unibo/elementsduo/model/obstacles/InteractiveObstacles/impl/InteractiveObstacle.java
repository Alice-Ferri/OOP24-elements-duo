package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;

public abstract class InteractiveObstacle {

    HitBox hitbox;

    public InteractiveObstacle(HitBox hitbox) {
        this.hitbox = hitbox;
    }

}
