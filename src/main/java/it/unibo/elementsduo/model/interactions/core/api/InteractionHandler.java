package it.unibo.elementsduo.model.interactions.core.api;

import it.unibo.elementsduo.model.interactions.core.impl.InteractionResponse;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionInformations;

/**
 * Defines the behavior of a collision handler responsible for managing
 * interactions
 * between two {@link Collidable} objects after a collision has been detected.
 * 
 * <p>
 * Implementations determine whether they can handle a specific pair of
 * colliding objects
 * and define how to respond to the collision by adding commands to a
 * {@link InteractionResponse.Builder}.
 */
public interface InteractionHandler {

    /**
     * Determines whether this handler can process a collision between the given
     * collidable objects.
     *
     * @param a the first collidable object
     * @param b the second collidable object
     * @return {@code true} if this handler can manage the collision, {@code false}
     *         otherwise
     */
    boolean canHandle(Collidable a, Collidable b);

    /**
     * Handles the collision between two collidable objects, generating appropriate
     * responses
     * and adding them to the provided {@link InteractionResponse.Builder}.
     *
     * @param c       the {@link CollisionInformations} describing the collision
     * @param builder the {@link InteractionResponse.Builder} used to collect
     *                resulting actions
     */
    void handle(CollisionInformations c, InteractionResponse.Builder builder);

    /**
     * Called before collision processing starts for a new update cycle.
     * 
     * <p>
     * Can be overridden by handlers that need to perform pre-update setup.
     */
    default void onUpdateStart() {
    }

    /**
     * Called after all collisions have been processed for a given update cycle.
     * 
     * <p>
     * Can be overridden by handlers that need to perform cleanup or state tracking.
     */
    default void onUpdateEnd() {
    }
}
