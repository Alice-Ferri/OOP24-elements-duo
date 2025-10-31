package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

public class WaterPool extends StaticObstacle implements Hazard {

    public WaterPool(HitBox hitBox) {
        super(hitBox);
    }

}