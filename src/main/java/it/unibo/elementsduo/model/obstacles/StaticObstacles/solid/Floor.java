package it.unibo.elementsduo.model.obstacles.StaticObstacles.solid;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents a solid floor obstacle in the game world.
 * 
 * <p>
 * The {@code Floor} acts as a surface on which entities such as players or
 * movable objects can stand.
 * 
 * </p>
 */
public final class Floor extends AbstractStaticObstacle {

    /**
     * Creates a new {@code Floor} with the specified hitbox.
     *
     * @param hitbox the {@link HitBox} defining the floor’s position and boundaries
     */
    public Floor(final HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.STATIC_OBSTACLE;
    }
}
