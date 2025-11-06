package it.unibo.elementsduo.model.obstacles.staticobstacles.solid;

import it.unibo.elementsduo.model.interactions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;

/**
 * Represents a solid wall obstacle in the game world.
 * 
 */
public final class Wall extends AbstractStaticObstacle {

    /**
     * Creates a new {@code Wall} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} of the wall
     */
    public Wall(final HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.STATIC_OBSTACLE;
    }

}
