package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

public abstract class StaticObstacle implements obstacle {
    private HitBox hitbox;

    public StaticObstacle(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }
}
