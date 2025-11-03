package it.unibo.elementsduo.model.collisions.detection.api;

import java.util.List;

import it.unibo.elementsduo.model.collisions.detection.impl.BoundingBox;
import it.unibo.elementsduo.model.collisions.detection.impl.QuadObj;

/**
 * Represents a spatial partitioning data structure used to efficiently manage
 * and query objects in a two-dimensional space.
 */
public interface QuadTree {

    /**
     * Inserts the specified {@link QuadObj} into the quadtree.
     *
     * @param entry the object to insert
     */
    void insert(QuadObj entry);

    /**
     * Retrieves all potential objects that may intersect with the given entry.
     *
     * @param result the list in which retrieved objects are stored
     * @param entry  the reference object used for collision queries
     */
    void retrieve(List<QuadObj> result, QuadObj entry);

    /**
     * Checks whether this quadtree node intersects with the given bounding box.
     *
     * @param bb the bounding box to test
     * @return {@code true} if the quadtree intersects the bounding box,
     *         {@code false} otherwise
     */
    boolean intersects(BoundingBox bb);
}
