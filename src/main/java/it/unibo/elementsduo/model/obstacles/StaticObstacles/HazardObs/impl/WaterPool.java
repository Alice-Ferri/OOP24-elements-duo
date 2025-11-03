package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.api.HazardEffect;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.impl.KillEffect;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents a pool of water that acts as a hazard for certain players.
 * 
 * <p>
 * This obstacle can cause a player (such as Fireboy) to die upon contact.
 * 
 * </p>
 */
public final class WaterPool extends AbstractStaticObstacle implements Hazard {

    private final HazardEffect effect;

    /**
     * Creates a new {@code WaterPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining the water pool’s position and
     *               boundaries
     */
    public WaterPool(final HitBox hitBox) {
        super(hitBox);
        this.effect = new KillEffect();
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.WATER;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.HAZARD;
    }

    @Override
    public HazardEffect getEffect() {
        return this.effect;
    }
}
