package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

public class Wall extends AbstractStaticObstacle {
    HitBox hitBox;

    public Wall(HitBox hitBox) {
        super(hitBox);
    }
}
