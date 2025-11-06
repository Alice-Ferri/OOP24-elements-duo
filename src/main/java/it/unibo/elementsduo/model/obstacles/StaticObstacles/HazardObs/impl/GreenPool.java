package it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.impl;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api.Hazard;

/**
 * Represents a green toxic pool hazard in the game world.
 * 
 * <p>
 * This obstacle instantly kills both players upon contact and cannot be
 * interacted with.
 */
public final class GreenPool extends AbstractStaticObstacle implements Hazard {

    /**
     * Constructs a new {@code GreenPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} of the GreenPool
     */
    public GreenPool(final HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.POISON;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.HAZARD;
    }

}
