package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents the water exit zone in the game world.
 * 
 * <p>
 * This exit can be activated when
 * {@link it.unibo.elementsduo.model.player.impl.Watergirl}
 * reaches it. It is a non-solid obstacle that acts as a goal area.
 */
public final class WaterExit extends AbstractStaticObstacle implements ExitZone {

    private boolean active;

    /**
     * Creates a new {@code WaterExit} with the specified hitbox.
     *
     * @param hitbox the hitbox defining the position and dimensions of the exit
     */
    public WaterExit(final HitBox hitbox) {
        super(hitbox);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Water exits are non-solid, allowing players to overlap them.
     *
     * @return {@code false} because this object is not solid
     */
    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * Activates the exit zone, marking it as triggered.
     * 
     * <p>
     * This typically occurs when Watergirl successfully reaches the exit.
     */
    @Override
    public void activate() {
        this.active = true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} if the water exit has been activated, {@code false}
     *         otherwise
     */
    @Override
    public boolean isActive() {
        return this.active;
    }
}
