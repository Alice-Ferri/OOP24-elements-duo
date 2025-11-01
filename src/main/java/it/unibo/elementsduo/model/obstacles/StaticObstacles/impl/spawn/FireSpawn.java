package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.spawn;

import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents the spawn point for the Fireboy character in the game world.
 * 
 * <p>
 * This obstacle defines the starting position of Fireboy and is not solid,
 * allowing entities to pass through it.
 * 
 * </p>
 */
public final class FireSpawn extends AbstractStaticObstacle {

    /**
     * Creates a new {@code FireSpawn} instance with the specified hitbox.
     *
     * @param hitbox the {@link HitBox} defining the spawn area
     */
    public FireSpawn(final HitBox hitbox) {
        super(hitbox);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * The spawn point is not solid, so it does not block movement.
     * 
     * </p>
     *
     * @return always {@code false}
     */
    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }
}
