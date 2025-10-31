package it.unibo.elementsduo.model.obstacles.StaticObstacles.api;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

/**
 * Abstract base class for all static (non-moving) obstacles in the game,
 * implementing the basic functionality of an obstacle with a fixed HitBox.
 */
public abstract class StaticObstacle implements obstacle {
    private final HitBox hitbox;

    public StaticObstacle(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    /**
     * {@inheritDoc}
     * Returns the obstacle's HitBox.
     *
     * * @return The HitBox of the obstacle.
     */
    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }
}
