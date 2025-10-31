package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;

/**
 * Represents a pool of water that acts as a hazard for certain players.
 * 
 * <p>
 * This obstacle can cause a player (such as Fireboy) to die upon contact.
 * 
 * </p>
 */
public final class WaterPool extends AbstractStaticObstacle implements Hazard {

    /**
     * Creates a new {@code WaterPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining the water pool’s position and
     *               boundaries
     */
    public WaterPool(final HitBox hitBox) {
        super(hitBox);
    }
}
