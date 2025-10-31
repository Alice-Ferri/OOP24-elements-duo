package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents the exit point that must be
 * activated by one of the players before proceeding to the next level.
 */
public class FireExit extends AbstractStaticObstacle implements ExitZone {

    /** Whether this exit has been activated. */
    private boolean active;

    /**
     * Creates a new {@code FireExit} object.
     * 
     * <p>
     *
     * @param hitBox the {@link HitBox} defining the exit zone's position and
     *               boundaries
     */
    public FireExit(final HitBox hitBox) {
        super(hitBox);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Returns {@code false}, as the exit zone should not block player movement.
     *
     * @return always {@code false}
     */
    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * Activates the exit, allowing the player to complete the level when colliding
     * with it.
     */
    @Override
    public void activate() {
        this.active = true;
    }

    /**
     * Checks if the exit zone is currently active.
     * 
     * <p>
     *
     * @return {@code true} if the exit is active, {@code false} otherwise
     */
    @Override
    public boolean isActive() {
        return this.active;
    }
}
