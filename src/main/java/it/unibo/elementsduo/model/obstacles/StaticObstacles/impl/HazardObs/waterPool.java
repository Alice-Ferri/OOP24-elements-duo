package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;

public class WaterPool extends AbstractStaticObstacle implements Hazard {

    public WaterPool(HitBox hitBox) {
        super(hitBox);
    }

}