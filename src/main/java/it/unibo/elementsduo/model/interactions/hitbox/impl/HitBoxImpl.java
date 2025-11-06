package it.unibo.elementsduo.model.interactions.hitbox.impl;

import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.resources.Position;

/**
 * Default implementation of the {@link HitBox} interface.
 * Represents a rectangular hitbox defined by its center position, height, and
 * width.
 * Provides basic intersection detection functionality.
 */
public final class HitBoxImpl implements HitBox {

    private final Position center;
    private final double height;
    private final double width;

    /**
     * Creates a new {@code HitBoxImpl} with the specified center position and
     * dimensions.
     *
     * @param center the center position of the hitbox
     * @param height the full height of the hitbox
     * @param width  the full width of the hitbox
     */
    public HitBoxImpl(final Position center, final double height, final double width) {
        this.center = center;
        this.height = height;
        this.width = width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCenter() {
        return this.center;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHalfHeight() {
        return this.height / 2.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHalfWidth() {
        return this.width / 2.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(final HitBox otherHitBox) {
        return Math.abs(center.x() - otherHitBox.getCenter().x()) <= otherHitBox.getHalfWidth() + getHalfWidth()
                && Math.abs(center.y() - otherHitBox.getCenter().y()) <= otherHitBox.getHalfHeight() + getHalfHeight();
    }
}
