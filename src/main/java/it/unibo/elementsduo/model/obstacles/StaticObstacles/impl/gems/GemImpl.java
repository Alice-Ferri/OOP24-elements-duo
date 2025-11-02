package it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.gems;

import java.util.EnumSet;

import it.unibo.elementsduo.model.collisions.core.api.CollisionLayer;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.hitbox.impl.HitBoxImpl;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.resources.Position;

/**
 * Implementation of a collectible gem object.
 *
 * <p>
 * Each {@code GemImpl} instance represents a collectible item that can be
 * picked up by a player. Once collected, the gem becomes inactive.
 * </p>
 */
public final class GemImpl implements Gem {

    /** Default size of the gem hitbox. */
    private static final double GEM_SIZE = 0.5;

    /** The hitbox representing the gem's position and size. */
    private final HitBox hitbox;

    /** Whether the gem is still active (not yet collected). */
    private boolean active = true;

    /**
     * Creates a new gem at the specified position.
     *
     * @param pos the position where the gem will be placed
     */
    public GemImpl(final Position pos) {
        this.hitbox = new HitBoxImpl(pos, GEM_SIZE, GEM_SIZE);
    }

    /**
     * {@inheritDoc}
     *
     * @return the {@link HitBox} representing the gem’s area
     */
    @Override
    public HitBox getHitBox() {
        return this.hitbox;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@code true} if the gem is still active and can be collected,
     *         {@code false} otherwise
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Marks the gem as collected, making it inactive.
     * </p>
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

    @Override
    public EnumSet<CollisionLayer> getCollisionMask() {
        return EnumSet.of(CollisionLayer.PLAYER);
    }
}
