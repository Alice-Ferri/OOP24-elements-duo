package it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.impl;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api.Hazard;

/**
 * Represents a pool of water that acts as deadly hazard for fireboy.
 * 
 */
public final class WaterPool extends AbstractStaticObstacle implements Hazard {

    /**
     * Creates a new {@code WaterPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} of the waterpool
     */
    public WaterPool(final HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.WATER;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.HAZARD;
    }

}
