package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.solid;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

public class Wall extends StaticObstacle {
    HitBox hitBox;

    public Wall(HitBox hitBox) {
        super(hitBox);
    }
}
