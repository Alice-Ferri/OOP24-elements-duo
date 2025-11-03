package it.unibo.elementsduo.model.obstacles.StaticObstacles.solid;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.AbstractStaticObstacle;

/**
 * Represents a solid wall obstacle in the game world.
 * 
 * <p>
 * The {@code Wall} acts as a blocking element that prevents entities
 * from passing through it.
 * 
 * </p>
 */
public final class Wall extends AbstractStaticObstacle {

    /**
     * Creates a new {@code Wall} with the specified hitbox.
     *
     * @param hitBox the {@link HitBox} defining the wall’s position and boundaries
     */
    public Wall(final HitBox hitBox) {
        super(hitBox);
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.STATIC_OBSTACLE;
    }

}
