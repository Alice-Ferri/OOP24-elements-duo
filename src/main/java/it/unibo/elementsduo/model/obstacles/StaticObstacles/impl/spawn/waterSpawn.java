package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

public class waterSpawn extends AbstractStaticObstacle {

    public waterSpawn(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
