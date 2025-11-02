package it.unibo.elementsduo.model.powerups.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.powerups.api.PowerUp;
import it.unibo.elementsduo.model.powerups.api.PowerUpType;
import it.unibo.elementsduo.resources.Position;

public class PowerUpImpl implements PowerUp {

    private final PowerUpType type;
    private final double duration;
    private final HitBox hitBox;
    private boolean active = true;

    public PowerUpImpl(PowerUpType type, Position pos, double duration) {
        this.type = type;
        this.hitBox = new HitBoxImpl(pos, 0.3, 0.3);
        this.duration = duration;
    }

    @Override
    public HitBox getHitBox() {
        return this.hitBox;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.POWER_UP;
    }

    @Override
    public PowerUpType getType() {
        return this.type;
    }

    @Override
    public double getDuration() {
        return this.duration;
    }

    @Override
    public void consume() {
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

}
