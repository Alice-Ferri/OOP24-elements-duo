package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;

/**
 * Represents a green toxic pool hazard in the game world.
 * 
 * <p>
 * This obstacle instantly kills both players upon contact and cannot be
 * interacted with.
 */
public final class GreenPool extends AbstractStaticObstacle implements Hazard {

    /** The hitbox defining the position and dimensions of the pool. */
    private final HitBox hitbox;

    /**
     * Constructs a new {@code GreenPool} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining this pool’s location and size
     */
    public GreenPool(final HitBox hitBox) {
        super(hitBox);
        this.hitbox = hitBox;
    }

    @Override
    public HazardType getHazardType() {
        return HazardType.POISON;
    }
}
