package it.unibo.elementsduo.model.interactions.detection.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.elementsduo.model.interactions.core.api.Collidable;
import it.unibo.elementsduo.model.interactions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.interactions.core.impl.CollisionInformationsImpl;
import it.unibo.elementsduo.model.interactions.detection.api.CollisionChecker;
import it.unibo.elementsduo.model.interactions.detection.api.QuadTree;
import it.unibo.elementsduo.model.interactions.hitbox.api.HitBox;
import it.unibo.elementsduo.resources.Vector2D;

/**
 * Class to checks collisions between onjects in the game.
 */

public class CollisionCheckerImpl implements CollisionChecker {

    private static final double MIN_BOUND_SPAN = 1.0;

    /**
     * Detects collisions between collidable entities.
     *
     * @param entities list of collidable objects
     * @return list of collision informations
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

        final double px = hitBoxA.getHalfWidth() + hitBoxB.getHalfWidth() - Math.abs(dx);
        final double py = hitBoxA.getHalfHeight() + hitBoxB.getHalfHeight() - Math.abs(dy);

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
        final var hitBox = collidable.getHitBox();
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

        final double spanX = maxX - minX;
        final double spanY = maxY - minY;

        if (spanX < MIN_BOUND_SPAN) {
            final double deltaX = (MIN_BOUND_SPAN - spanX) / 2.0;
            minX -= deltaX;
            maxX += deltaX;
        }

        if (spanY < MIN_BOUND_SPAN) {
            final double deltaY = (MIN_BOUND_SPAN - spanY) / 2.0;
            minY -= deltaY;
            maxY += deltaY;
        }

        return new BoundingBox(minX, minY, maxX, maxY);
    }
}
