package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

public class waterExit extends StaticObstacle implements ExitZone {

    private boolean active = false;

    public waterExit(HitBox hitbox) {
        super(hitbox);
    }

    @Override
<<<<<<< HEAD
    public boolean isSolid() {
        return false;
=======
    public void activate() {
        this.active=true;
    }

    @Override
    public boolean isActive() {
        return this.active;
>>>>>>> e45e65b248549db1bca64aab0f2ce09f7ec33972
    }
}
