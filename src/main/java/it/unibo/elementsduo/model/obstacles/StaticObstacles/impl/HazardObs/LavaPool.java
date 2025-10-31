package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents a pool of lava that acts as a deadly hazard
 * for players who come into contact with it.
 */
public final class LavaPool extends AbstractStaticObstacle implements Hazard {

    /**
     * Creates a new {@code LavaPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining the lava pool’s position and
     *               boundaries
     */
    public LavaPool(final HitBox hitBox) {
        super(hitBox);
    }
}
