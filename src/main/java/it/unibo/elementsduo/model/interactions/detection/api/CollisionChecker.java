package it.unibo.elementsduo.model.interactions.detection.api;

import java.util.List;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.api.CollisionInformations;

/**
 * Defines the contract for checking collisions among a collection of
 * {@link Collidable} objects.
 * 
 * <p>
 * Implementations of this interface detect intersections and return information
 * about
 * collisions that occurred during a single update cycle.
 */
@FunctionalInterface
public interface CollisionChecker {

    /**
     * Checks all potential collisions among the provided list of entities.
     *
     * @param entities the list of {@link Collidable} objects to check for
     *                 collisions
     * @return a list of {@link CollisionInformations} representing detected
     *         collisions
     */
    List<CollisionInformations> checkCollisions(List<Collidable> entities);
}
