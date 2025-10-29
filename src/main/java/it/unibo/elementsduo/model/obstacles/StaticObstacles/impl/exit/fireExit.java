package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

public class fireExit extends StaticObstacle implements ExitZone {

    private boolean active = false;
    
    public fireExit(HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public void activate() {
        this.active=true;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }
}