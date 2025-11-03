package it.unibo.elementsduo.model.collisions.detection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.collisions.hitbox.api.HitBox;
import it.unibo.elementsduo.model.collisions.detection.api.QuadTree;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Implementation of {@link CollisionChecker} that detects and resolves
 * collisions between {@link Collidable} entities using a QuadTree for spatial
 * partitioning.
 * 
 * <p>
 * This class is final and not designed for extension.
 * </p>
 */
public final class CollisionCheckerImpl implements CollisionChecker {

    private static final double MIN_BOUND_SPAN = 1.0;

    /**
     * Checks for collisions among the provided collidable entities.
     * 
     * <p>
     * The method uses a QuadTree structure to efficiently find possible collision
     * pairs and computes collision information for objects whose hitboxes
     * intersect.
     * </p>
     *
     * @param entities the list of collidable entities to check for collisions
     * @return a list of {@link CollisionInformations} representing detected
     *         collisions
     */
    @Override
    public List<CollisionInformations> checkCollisions(final List<Collidable> entities) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }

        final List<QuadObj> entries = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            final Collidable collidable = entities.get(i);
            entries.add(new QuadObj(collidable, boundsFor(collidable), i));
        }

        final BoundingBox worldBounds = computeWorldBounds(entries);
        final QuadTree quadTree = new QuadTreeImpl(worldBounds);

        for (final QuadObj entry : entries) {
            quadTree.insert(entry);
        }

        final List<CollisionInformations> informations = new ArrayList<>();
        final List<QuadObj> candidates = new ArrayList<>();

        for (final QuadObj entry : entries) {
            candidates.clear();
            quadTree.retrieve(candidates, entry);

            for (final QuadObj candidate : candidates) {
                if (candidate.index() <= entry.index()) {
                    continue;
                }

                checkCollisionBetweenTwoObjects(entry.collidable(), candidate.collidable())
                        .ifPresent(informations::add);
            }
        }

        return informations;
    }

    private Optional<CollisionInformations> checkCollisionBetweenTwoObjects(final Collidable objectA,
            final Collidable objectB) {
        if (!objectA.getHitBox().intersects(objectB.getHitBox())) {
            return Optional.empty();
        }

        final HitBox hitBoxA = objectA.getHitBox();
        final HitBox hitBoxB = objectB.getHitBox();

        final double dx = hitBoxA.getCenter().x() - hitBoxB.getCenter().x();
        final double dy = hitBoxA.getCenter().y() - hitBoxB.getCenter().y();

        final double px = (hitBoxA.getHalfWidth() + hitBoxB.getHalfWidth()) - Math.abs(dx);
        final double py = (hitBoxA.getHalfHeight() + hitBoxB.getHalfHeight()) - Math.abs(dy);

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

    private static BoundingBox boundsFor(final Collidable collidable) {
        final HitBox hitBox = collidable.getHitBox();
        final var center = hitBox.getCenter();
        final double halfWidth = hitBox.getHalfWidth();
        final double halfHeight = hitBox.getHalfHeight();
        final double minX = center.x() - halfWidth;
        final double maxX = center.x() + halfWidth;
        final double minY = center.y() - halfHeight;
        final double maxY = center.y() + halfHeight;
        return new BoundingBox(minX, minY, maxX, maxY);
    }

    private static BoundingBox computeWorldBounds(final List<QuadObj> entries) {
        if (entries.isEmpty()) {
            return new BoundingBox(0, 0, MIN_BOUND_SPAN, MIN_BOUND_SPAN);
        }

        final BoundingBox firstBounds = entries.get(0).bb();
        double minX = firstBounds.minX();
        double maxX = firstBounds.maxX();
        double minY = firstBounds.minY();
        double maxY = firstBounds.maxY();

        for (int i = 1; i < entries.size(); i++) {
            final BoundingBox bounds = entries.get(i).bb();
            minX = Math.min(minX, bounds.minX());
            maxX = Math.max(maxX, bounds.maxX());
            minY = Math.min(minY, bounds.minY());
            maxY = Math.max(maxY, bounds.maxY());
        }

        if (minX == maxX) {
            minX -= MIN_BOUND_SPAN / 2.0;
            maxX += MIN_BOUND_SPAN / 2.0;
        }

        if (minY == maxY) {
            minY -= MIN_BOUND_SPAN / 2.0;
            maxY += MIN_BOUND_SPAN / 2.0;
        }

        return new BoundingBox(minX, minY, maxX, maxY);
    }
}
