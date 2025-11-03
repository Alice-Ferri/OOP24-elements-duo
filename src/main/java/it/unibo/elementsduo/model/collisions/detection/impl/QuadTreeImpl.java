package it.unibo.elementsduo.model.collisions.detection.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.elementsduo.model.collisions.detection.api.QuadTree;

/**
 * Implementation of a QuadTree structure used for spatial partitioning of
 * objects in 2D space.
 * <p>
 * This implementation recursively subdivides space into four quadrants to
 * improve collision detection efficiency.
 * The class is declared final to prevent inheritance.
 */
public final class QuadTreeImpl implements QuadTree {
    private static final int MAX_OBJECTS_PER_NODE = 6;
    private static final int MAX_LEVELS = 6;

    private final int level;
    private final BoundingBox bounds;
    private final List<QuadObj> objects = new ArrayList<>();
    private final QuadTree[] nodes = new QuadTree[4];

    /**
     * Creates a new QuadTree instance at level 0 with the specified bounding box.
     *
     * @param bounds the bounding box that defines the area of this QuadTree
     */
    public QuadTreeImpl(final BoundingBox bounds) {
        this(0, bounds);
    }

    /**
     * Creates a new QuadTree instance at a specific level with the given bounding
     * box.
     *
     * @param level  the current depth level of the QuadTree
     * @param bounds the bounding box that defines the area of this QuadTree
     */
    private QuadTreeImpl(final int level, final BoundingBox bounds) {
        this.level = level;
        this.bounds = bounds;
    }

    /**
     * Inserts an object into the QuadTree. If necessary, the QuadTree will
     * subdivide and
     * redistribute objects among its child nodes.
     *
     * @param entry the object to insert
     */
    public void insert(final QuadObj entry) {
        if (nodes[0] != null) {
            final int index = getIndex(entry.bb());
            if (index != -1) {
                nodes[index].insert(entry);
                return;
            }
        }

        objects.add(entry);

        if (objects.size() > MAX_OBJECTS_PER_NODE && level < MAX_LEVELS && bounds.width() > 0 && bounds.height() > 0) {
            if (nodes[0] == null) {
                split();
            }

            final Iterator<QuadObj> iterator = objects.iterator();
            while (iterator.hasNext()) {
                final QuadObj stored = iterator.next();
                final int index = getIndex(stored.bb());
                if (index != -1) {
                    nodes[index].insert(stored);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Retrieves all potential collision candidates for a given object.
     *
     * @param result the list where results will be stored
     * @param entry  the object for which to find possible collisions
     */
    public void retrieve(final List<QuadObj> result, final QuadObj entry) {
        final int index = getIndex(entry.bb());
        if (nodes[0] != null) {
            if (index != -1) {
                nodes[index].retrieve(result, entry);
            } else {
                for (final QuadTree node : nodes) {
                    if (node != null && node.intersects(entry.bb())) {
                        node.retrieve(result, entry);
                    }
                }
            }
        }

        result.addAll(objects);
    }

    /**
     * Checks if the given bounding box intersects with this QuadTree's bounds.
     *
     * @param bb the bounding box to check
     * @return true if there is an intersection, false otherwise
     */
    public boolean intersects(final BoundingBox bb) {
        return this.bounds.intersects(bb);
    }

    /**
     * Splits the current QuadTree node into four subnodes.
     */
    private void split() {
        final double subWidth = bounds.width() / 2.0;
        final double subHeight = bounds.height() / 2.0;
        final double x = bounds.minX();
        final double y = bounds.minY();

        nodes[0] = new QuadTreeImpl(level + 1, new BoundingBox(x + subWidth, y, x + 2 * subWidth, y + subHeight));
        nodes[1] = new QuadTreeImpl(level + 1, new BoundingBox(x, y, x + subWidth, y + subHeight));
        nodes[2] = new QuadTreeImpl(level + 1, new BoundingBox(x, y + subHeight, x + subWidth, y + 2 * subHeight));
        nodes[3] = new QuadTreeImpl(level + 1,
                new BoundingBox(x + subWidth, y + subHeight, x + 2 * subWidth, y + 2 * subHeight));
    }

    /**
     * Determines the index of the quadrant in which a given bounding box should be
     * placed.
     *
     * @param box the bounding box to evaluate
     * @return the index of the quadrant, or -1 if it does not fit entirely within
     *         one quadrant
     */
    private int getIndex(final BoundingBox box) {
        final double midPointVert = bounds.minX() + bounds.width() / 2.0;
        final double midPointOriz = bounds.minY() + bounds.height() / 2.0;

        final boolean topQuadrant = box.maxY() <= midPointOriz;
        final boolean bottomQuadrant = box.minY() >= midPointOriz;

        if (box.maxX() <= midPointVert) {
            if (topQuadrant) {
                return 1;
            }
            if (bottomQuadrant) {
                return 2;
            }
        } else if (box.minX() >= midPointVert) {
            if (topQuadrant) {
                return 0;
            }
            if (bottomQuadrant) {
                return 3;
            }
        }

        return -1;
    }
}
