package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.impl;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;
import it.unibo.elementsduo.resources.Position;

/**
 * Base class for interactive obstacles in the game world.
 *
 * <p>
 * Provides common properties such as position and dimensions, and a default
 * implementation for the obstacle's {@link HitBox}.
 *
 * <p>
 * Specific interactive elements (e.g., buttons, levers) should extend this
 * class and define their own behavior.
 */
public abstract class AbstractInteractiveObstacle implements Obstacle {

    /** The center position of the obstacle. */
    private Position center;

    /** The half height of the obstacle. */
    private final double halfHeight;

    /** The half width of the obstacle. */
    private final double halfWidth;

    /**
     * Creates a new interactive obstacle.
     *
     * @param center the position of the obstacle's center
     * @param halfW  the half-width of the obstacle
     * @param halfH  the half-height of the obstacle
     */
    public AbstractInteractiveObstacle(final Position center, final double halfW, final double halfH) {
        this.center = center;
        this.halfWidth = halfW;
        this.halfHeight = halfH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HitBox getHitBox() {
        return new HitBoxImpl(center, 2 * halfHeight, 2 * halfWidth);
    }

    /**
     * Returns the center position of this obstacle.
     *
     * @return the center position
     */
    public Position getCenter() {
        return this.center;
    }

    /**
     * Sets the center position of this obstacle.
     *
     * @param center the new center position
     */
    protected void setCenter(final Position center) {
        this.center = center;
    }
}
