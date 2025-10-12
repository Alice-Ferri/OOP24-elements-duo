package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api.Triggerable;

public class Lever implements Triggerable, Collidable {

    HitBox hitbox;
    boolean active; // initialy not active

    public Lever(HitBox hitbox) {
        this.hitbox = hitbox;
        this.active = false;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitbox;
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

}
