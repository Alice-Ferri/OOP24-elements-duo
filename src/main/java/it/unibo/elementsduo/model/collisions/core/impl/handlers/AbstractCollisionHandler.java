package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Abstract base class for handling collisions between two {@link Collidable}
 * types.
 * 
 * <p>
 * This class provides generic collision dispatch logic that identifies whether
 * a pair of collidable objects can be processed by this handler and delegates
 * the actual collision handling to subclasses via the
 * {@link #handleCollision(Object, Object, CollisionInformations, CollisionResponse.Builder)}
 * method.
 * </p>
 * 
 * <p>
 * Subclasses should implement
 * {@link #handleCollision(Object, Object, CollisionInformations, CollisionResponse.Builder)}
 * to define specific collision behavior between two object types.
 * </p>
 *
 * @param <A> the first {@link Collidable} type
 * @param <B> the second {@link Collidable} type
 */
public abstract class AbstractCollisionHandler<A extends Collidable, B extends Collidable>
        implements CollisionHandler {

    /** The first collidable type handled by this handler. */
    private final Class<A> typeA;

    /** The second collidable type handled by this handler. */
    private final Class<B> typeB;

    /**
     * Creates a new collision handler for the specified pair of collidable types.
     *
     * @param typeA the class type of the first collidable
     * @param typeB the class type of the second collidable
     */
    public AbstractCollisionHandler(final Class<A> typeA, final Class<B> typeB) {
        this.typeA = typeA;
        this.typeB = typeB;
    }

    /**
     * Checks whether this handler can process a collision between the given
     * objects.
     * 
     * <p>
     * The handler can handle the collision if the pair of objects matches either
     * {@code (typeA, typeB)} or {@code (typeB, typeA)}.
     * </p>
     *
     * @param a the first collidable
     * @param b the second collidable
     * @return {@code true} if this handler can process the given pair,
     *         {@code false} otherwise
     */
    public boolean canHandle(final Collidable a, final Collidable b) {
        return typeA.isInstance(a) && typeB.isInstance(b)
                || typeA.isInstance(b) && typeB.isInstance(a);
    }

    /**
     * Handles a collision between two collidable objects if their types are
     * supported by this handler.
     * 
     * <p>
     * The method determines the correct type order and delegates the handling to
     * {@link #handleCollision(Object, Object, CollisionInformations, CollisionResponse.Builder)}.
     * </p>
     *
     * @param c       the collision information
     * @param builder the builder used to accumulate collision responses
     */
    public void handle(final CollisionInformations c, final CollisionResponse.Builder builder) {
        final Collidable a = c.getObjectA();
        final Collidable b = c.getObjectB();

        if (typeA.isInstance(a) && typeB.isInstance(b)) {
            this.handleCollision(typeA.cast(a), typeB.cast(b), c, builder);
        } else if (typeA.isInstance(b) && typeB.isInstance(a)) {
            this.handleCollision(typeA.cast(b), typeB.cast(a), c, builder);
        }
    }

    /**
     * Handles a specific collision between two objects of the supported types.
     * 
     * <p>
     * Subclasses must implement this method to define collision behavior between
     * the specified types.
     * </p>
     *
     * @param a       the first collidable object
     * @param b       the second collidable object
     * @param c       the collision information
     * @param builder the builder used to construct the collision response
     */
    protected abstract void handleCollision(A a, B b, CollisionInformations c, CollisionResponse.Builder builder);

    /**
     * Returns the collision normal vector from the perspective of a specific
     * {@link Collidable}.
     * 
     * <p>
     * If the given object is {@code objectA} in the collision, the normal is
     * returned as-is. Otherwise, the normal is inverted to reflect the opposite
     * direction from {@code objectB}'s perspective.
     * </p>
     *
     * @param perspectiveObj the {@link Collidable} for which the normal should be
     *                       computed
     * @param c              the {@link CollisionInformations} object containing
     *                       collision details
     * @return the normal vector from the given object's perspective
     */
    protected final Vector2D getNormalFromPerspective(final Collidable perspectiveObj, final CollisionInformations c) {
        return (c.getObjectA() == perspectiveObj) ? c.getNormal() : c.getNormal().multiply(-1);
    }
}
