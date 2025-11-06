package it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.impl;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.staticobstacles.hazardobs.api.Hazard;

/**
 * Represents a pool of lava that acts as a deadly hazard for watergirl.
 */
public final class LavaPool extends AbstractStaticObstacle implements Hazard {

    /**
     * Creates a new {@code LavaPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} of the lavaPool
     */
    public LavaPool(final HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.LAVA;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.HAZARD;
    }

}
