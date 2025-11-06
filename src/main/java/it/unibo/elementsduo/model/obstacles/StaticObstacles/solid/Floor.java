package it.unibo.elementsduo.model.obstacles.staticobstacles.solid;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;

/**
 * Represents a solid floor obstacle in the game world in which players can
 * stand.
 * 
 */
public final class Floor extends AbstractStaticObstacle {

    /**
     * Creates a new {@code Floor} with the specified hitbox.
     *
     * @param hitbox the {@link HitBox} the floor
     */
    public Floor(final HitBox hitbox) {
        super(hitbox);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.STATIC_OBSTACLE;
    }
}
