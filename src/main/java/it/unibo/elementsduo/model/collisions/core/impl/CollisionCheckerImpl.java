package it.unibo.elementsduo.model.collisions.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Default implementation of the {@link CollisionChecker} interface.
 * 
 * <p>
 * Detects collisions between pairs of {@link Collidable} entities using
 * axis-aligned bounding box (AABB) intersection logic.
 */
public final class CollisionCheckerImpl implements CollisionChecker {

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Checks all pairs of collidable entities and returns a list of
     * {@link CollisionInformations} representing the detected collisions.
     *
     * @param entities the list of collidable entities to check
     * @return a list of detected collisions
     */
    @Override
    public List<CollisionInformations> checkCollisions(final List<Collidable> entities) {
        final List<CollisionInformations> informations = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            for (int k = i + 1; k < entities.size(); k++) {
                final Optional<CollisionInformations> tmp = checkCollisionBetweenTwoObjects(entities.get(i),
                        entities.get(k));
                if (tmp.isPresent()) {
                    informations.add(tmp.get());
                }
            }
        }
        return informations;
    }

    /**
     * Checks if two {@link Collidable} objects are colliding and, if so,
     * computes the collision information between them.
     *
     * @param objectA the first collidable object
     * @param objectB the second collidable object
     * @return an {@link Optional} containing the collision information if a
     *         collision is detected,
     *         or an empty {@link Optional} otherwise
     */
    private Optional<CollisionInformations> checkCollisionBetweenTwoObjects(
            final Collidable objectA, final Collidable objectB) {

        if (!objectA.getHitBox().intersects(objectB.getHitBox())) {
            return Optional.empty();
        }

        // Calculate distance between centers
        final double dx = objectA.getHitBox().getCenter().x() - objectB.getHitBox().getCenter().x();
        final double dy = objectA.getHitBox().getCenter().y() - objectB.getHitBox().getCenter().y();

        // Calculate overlap on both axes
        final double px = (objectA.getHitBox().getHalfWidth() + objectB.getHitBox().getHalfWidth()) - Math.abs(dx);
        final double py = (objectA.getHitBox().getHalfHeight() + objectB.getHitBox().getHalfHeight()) - Math.abs(dy);

        // Determine minimum penetration axis and normal direction
        final double penetration;
        final Vector2D normal;

        if (px < py) {
            penetration = px;
            normal = new Vector2D(dx > 0 ? 1 : -1, 0);
        } else {
            penetration = py;
            normal = new Vector2D(0, dy > 0 ? 1 : -1);
        }

        return Optional.of(new CollisionInformationsImpl(objectA, objectB, penetration, normal));
    }
}
