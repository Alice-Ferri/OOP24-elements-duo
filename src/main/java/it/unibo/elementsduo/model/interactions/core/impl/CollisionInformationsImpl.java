package it.unibo.elementsduo.model.interactions.core.impl;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.api.CollisionInformations;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Default implementation of the {@link CollisionInformations} interface.
 * 
 * <p>
 * Stores details about a collision between two {@link Collidable} objects,
 * including the penetration depth and the collision normal.
 */
public final class CollisionInformationsImpl implements CollisionInformations {

    private final Collidable objectA;
    private final Collidable objectB;
    private final double penetration;
    private final Vector2D normal;

    /**
     * Creates a new {@code CollisionInformationsImpl} instance with the given data.
     *
     * @param objectA     the first collidable object
     * @param objectB     the second collidable object
     * @param penetration the penetration depth between the two objects
     * @param normal      the collision normal vector
     */
    public CollisionInformationsImpl(final Collidable objectA, final Collidable objectB,
            final double penetration, final Vector2D normal) {
        this.objectA = objectA;
        this.objectB = objectB;
        this.penetration = penetration;
        this.normal = normal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collidable getObjectA() {
        return this.objectA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collidable getObjectB() {
        return this.objectB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPenetration() {
        return this.penetration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2D getNormal() {
        return this.normal;
    }
}
