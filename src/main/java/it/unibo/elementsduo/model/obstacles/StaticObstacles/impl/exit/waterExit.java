package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

public class WaterExit extends StaticObstacle implements ExitZone {

    private boolean active = false;

    public WaterExit(HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void activate() {
        this.active = true;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }
}
