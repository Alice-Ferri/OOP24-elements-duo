package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid.Wall;
import it.unibo.elementsduo.resources.Position;

public class waterSpawn extends StaticObstacle {

    public waterSpawn(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
