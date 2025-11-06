package it.unibo.elementsduo.model.obstacles.staticObstacles.gem.impl;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.impl.AbstractStaticObstacle;
import it.unibo.elementsduo.model.obstacles.staticObstacles.gem.api.Gem;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of a collectible gem object.
 *
 * <p>
 * Each {@code GemImpl} instance represents a collectible item that can be
 * picked up by a player. Once collected, the gem becomes inactive.
 * </p>
 */
public final class GemImpl extends AbstractStaticObstacle implements Gem {

    private static final double GEM_SIZE = 0.5;

    private boolean active = true;

    /**
     * Creates a new gem at the specified position.
     *
     * <p>
     * 
     * @param pos the position where the gem will be placed
     */
    public GemImpl(final Position pos) {
        super(new HitBoxImpl(pos, GEM_SIZE, GEM_SIZE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collect() {
        this.active = false;
    }

    @Override
    public boolean hasPhysicsResponse() {
        return false;
    }

    @Override
    public CollisionLayer getCollisionLayer() {
        return CollisionLayer.GEM;
    }

}
