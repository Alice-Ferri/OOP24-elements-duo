package it.unibo.elementsduo.model.interactions.core.impl.handlers;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.api.InteractionHandler;
import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Abstract base class for handling interactions between two {@link Collidable}
 * types.
 * 
 * <p>
 * This class provides generic interaction dispatch logic that identifies
 * whether
 * a pair of collidable objects can be processed by this handler and delegates
 * the actual collision handling to subclasses via the
 * {@link #handleCollision(Object, Object, CollisionInformations, InteractionResponse.Builder)}
 * method.
 * 
 * <p>
 * Subclasses should implement
 * {@link #handleCollision(Object, Object, CollisionInformations, InteractionResponse.Builder)}
 * to define specific interaction behavior between two object types.
 *
 * @param <A> the first {@link Collidable} type
 * @param <B> the second {@link Collidable} type
 */
public abstract class AbstractInteractionHandler<A extends Collidable, B extends Collidable>
        implements InteractionHandler {

    private final Class<A> typeA;

    private final Class<B> typeB;

    /**
     * Creates a new interaction handler for the specified pair of collidable types.
     *
     * @param typeA the class type of the first collidable
     * @param typeB the class type of the second collidable
     */
    public AbstractInteractionHandler(final Class<A> typeA, final Class<B> typeB) {
        this.typeA = typeA;
        this.typeB = typeB;
    }

    /**
     * Checks whether this handler can process a interaction between the given
     * objects.
     * 
     * <p>
     * The handler can handle the interaction if the pair of objects matches either
     * {@code (typeA, typeB)} or {@code (typeB, typeA)}.
     *
     * @param a the first collidable
     * @param b the second collidable
     * @return {@code true} if this handler can process the given pair,
     *         {@code false} otherwise
     */
    @Override
    public boolean canHandle(final Collidable a, final Collidable b) {
        return typeA.isInstance(a) && typeB.isInstance(b)
                || typeA.isInstance(b) && typeB.isInstance(a);
    }

    /**
     * Handles an interaction between two collidable objects if their types are
     * supported
     * by this handler.
     * 
     * <p>
     * The method determines the correct type order and delegates the handling to
     * {@link #handleCollision(Object, Object, CollisionInformations, InteractionResponse.Builder)}.
     *
     * @param c       the collision information
     * @param builder the builder used to accumulate collision responses
     */
    @Override
    public void handle(final CollisionInformations c, final InteractionResponse.Builder builder) {
        final Collidable a = c.getObjectA();
        final Collidable b = c.getObjectB();

        if (typeA.isInstance(a) && typeB.isInstance(b)) {
            this.handleInteraction(typeA.cast(a), typeB.cast(b), c, builder);
        } else if (typeA.isInstance(b) && typeB.isInstance(a)) {
            this.handleInteraction(typeA.cast(b), typeB.cast(a), c, builder);
        }
    }

    /**
     * Handles a specific interaction between two objects of the supported types.
     * 
     * <p>
     * Subclasses must implement this method to define interaction behavior between
     * the specified types.
     *
     * @param a       the first collidable object
     * @param b       the second collidable object
     * @param c       the collision information
     * @param builder the builder used to construct the collision response
     */
    protected abstract void handleInteraction(A a, B b, CollisionInformations c, InteractionResponse.Builder builder);

    /**
     * Returns the collision normal from the perspective of the specified object.
     *
     * <p>
     * If the provided {@code perspectiveObj} is the first object in the collision
     * pair, the normal is returned as-is. Otherwise, the normal is inverted.
     * </p>
     *
     * @param perspectiveObj the object whose perspective is used to interpret the
     *                       collision normal
     * @param c              the collision information
     * @return the collision normal from the given object's perspective
     */
    protected final Vector2D getNormalFromPerspective(final Collidable perspectiveObj, final CollisionInformations c) {
        return c.getObjectA().equals(perspectiveObj) ? c.getNormal() : c.getNormal().multiply(-1);
    }
}
