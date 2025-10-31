package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

public class fireSpawn extends AbstractStaticObstacle {

    public fireSpawn(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}