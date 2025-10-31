package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Abstract base class for all static (non-moving) obstacles in the game,
 * implementing the basic functionality of an obstacle with a fixed HitBox.
 */
public abstract class AbstractStaticObstacle implements obstacle {

    /** The obstacle's hitbox defining its physical boundaries. */
    private final HitBox hitbox;

    /**
     * Creates a new static obstacle with the specified {@link HitBox}.
     *
     * @param hitbox the hitbox defining the obstacle's position and size
     */
    public AbstractStaticObstacle(final HitBox hitbox) {
        this.hitbox = hitbox;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns the obstacle's {@link HitBox}.
     * </p>
     *
     * @return the {@link HitBox} of this obstacle
     */
    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }
}
