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

public class CollisionCheckerImpl implements CollisionChecker {

    private static final double MIN_BOUND_SPAN = 1.0;

    @Override
    public List<CollisionInformations> checkCollisions(final List<Collidable> entities) {
        if (entities.isEmpty()) {
            return new ArrayList<>();
        }

        List<QuadObj> entries = new ArrayList<>(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            Collidable collidable = entities.get(i);
            entries.add(new QuadObj(collidable, boundsFor(collidable), i));
        }

        BoundingBox worldBounds = computeWorldBounds(entries);
        QuadTree quadTree = new QuadTreeImpl(worldBounds);

        for (QuadObj entry : entries) {
            quadTree.insert(entry);
        }

        List<CollisionInformations> informations = new ArrayList<>();
        List<QuadObj> candidates = new ArrayList<>();

        for (QuadObj entry : entries) {
            candidates.clear();
            quadTree.retrieve(candidates, entry);

            for (QuadObj candidate : candidates) {
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

        double dx = hitBoxA.getCenter().x() - hitBoxB.getCenter().x();
        double dy = hitBoxA.getCenter().y() - hitBoxB.getCenter().y();

        double px = (hitBoxA.getHalfWidth() + hitBoxB.getHalfWidth()) - Math.abs(dx);
        double py = (hitBoxA.getHalfHeight() + hitBoxB.getHalfHeight()) - Math.abs(dy);

        double penetration;
        Vector2D normal;

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
        var hitBox = collidable.getHitBox();
        var center = hitBox.getCenter();
        double halfWidth = hitBox.getHalfWidth();
        double halfHeight = hitBox.getHalfHeight();
        double minX = center.x() - halfWidth;
        double maxX = center.x() + halfWidth;
        double minY = center.y() - halfHeight;
        double maxY = center.y() + halfHeight;
        return new BoundingBox(minX, minY, maxX, maxY);
    }

    private static BoundingBox computeWorldBounds(final List<QuadObj> entries) {
        if (entries.isEmpty()) {
            return new BoundingBox(0, 0, MIN_BOUND_SPAN, MIN_BOUND_SPAN);
        }

        BoundingBox firstBounds = entries.get(0).bb();
        double minX = firstBounds.minX();
        double maxX = firstBounds.maxX();
        double minY = firstBounds.minY();
        double maxY = firstBounds.maxY();

        for (int i = 1; i < entries.size(); i++) {
            BoundingBox bounds = entries.get(i).bb();
            minX = Math.min(minX, bounds.minX());
            maxX = Math.max(maxX, bounds.maxX());
            minY = Math.min(minY, bounds.minY());
            maxY = Math.max(maxY, bounds.maxY());
        }

        double spanX = maxX - minX;
        double spanY = maxY - minY;

        if (spanX < MIN_BOUND_SPAN) {
            double deltaX = (MIN_BOUND_SPAN - spanX) / 2.0;
            minX -= deltaX;
            maxX += deltaX;
        }

        if (spanY < MIN_BOUND_SPAN) {
            double deltaY = (MIN_BOUND_SPAN - spanY) / 2.0;
            minY -= deltaY;
            maxY += deltaY;
        }

        return new BoundingBox(minX, minY, maxX, maxY);
    }
}
