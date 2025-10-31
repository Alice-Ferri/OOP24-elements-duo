package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;

/**
 * Represents the exit point that must be
 * activated by one of the player before it can proceed to the next level.
 */
public class FireExit extends StaticObstacle implements ExitZone {

    private boolean active = false;

    /**
     * Creates a new FireExit object.
     * 
     * @param hitBox The HitBox defining the exit zone's position and boundaries.
     */
    public FireExit(HitBox hitBox) {
        super(hitBox);
    }

    /**
     * {@inheritDoc}
     * Returns false, as the exit zone should not block player movement.
     * 
     * @return Always returns false.
     */
    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * Activates the exit, allowing the player to complete the level if they collide
     * with it.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Checks if the exit zone is currently active.
     * 
     * @return true if the exit is active, false otherwise.
     */
    @Override
    public boolean isActive() {
        return this.active;
    }
}
