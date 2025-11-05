package it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;

/**
 * Represents the exit point that must be
 * activated by one of the players before proceeding to the next level.
 */
public final class FireExit extends AbstractStaticObstacle implements ExitZone {

    /** Whether this exit has been activated. */
    private boolean active;

    /**
     * Creates a new {@code FireExit} object.
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
     * </p>
     *
     * @return always {@code false}
     */
    @Override
    public boolean hasPhysicsResponse() {
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
     * @return {@code true} if the exit is active, {@code false} otherwise
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns the type of exit, which for this implementation is
     * {@link ExitType#FIRE_EXIT}.
     * </p>
     *
     * @return the fire exit type
     */
    @Override
    public ExitType getExitType() {
        return ExitType.FIRE_EXIT;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Returns the collision layer associated with exit zones.
     * </p>
     *
     * @return the {@link CollisionLayer#EXIT_ZONE} layer
     */
    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.EXIT_ZONE;
    }
}
