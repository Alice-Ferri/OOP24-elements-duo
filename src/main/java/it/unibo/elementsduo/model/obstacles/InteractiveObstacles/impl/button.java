package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;

public class button implements Triggerable, Collidable {

    private boolean active;
    private HitBox hitbox;

    public button(HitBox hitbox) {
        this.hitbox = hitbox;
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    @Override
    public void deactivate() {
        this.active = false;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

}
