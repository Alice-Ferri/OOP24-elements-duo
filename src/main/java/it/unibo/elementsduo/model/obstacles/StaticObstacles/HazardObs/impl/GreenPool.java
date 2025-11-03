package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.api.HazardEffect;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.impl.KillEffect;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents a green toxic pool hazard in the game world.
 * 
 * <p>
 * This obstacle instantly kills both players upon contact and cannot be
 * interacted with.
 */
public final class GreenPool extends AbstractStaticObstacle implements Hazard {

    private final HazardEffect effect;

    /**
     * Constructs a new {@code GreenPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining this pool’s location and size
     */
    public GreenPool(final HitBox hitBox) {
        super(hitBox);
        this.effect = new KillEffect();
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.POISON;
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
