package it.unibo.elementsduo.model.collisions.detection.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.elementsduo.model.collisions.detection.api.QuadTree;

public final class QuadTreeImpl implements QuadTree {
    private static final int MAX_OBJECTS_PER_NODE = 6;
    private static final int MAX_LEVELS = 6;

    private final int level;
    private final BoundingBox bounds;
    private final List<QuadObj> objects = new ArrayList<>();
    private final QuadTree[] nodes = new QuadTree[4];

    public QuadTreeImpl(final BoundingBox bounds) {
        this(0, bounds);
    }

    private QuadTreeImpl(final int level, final BoundingBox bounds) {
        this.level = level;
        this.bounds = bounds;
    }

    public void insert(final QuadObj entry) {
        if (nodes[0] != null) {
            int index = getIndex(entry.bb());
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

            Iterator<QuadObj> iterator = objects.iterator();
            while (iterator.hasNext()) {
                QuadObj stored = iterator.next();
                int index = getIndex(stored.bb());
                if (index != -1) {
                    nodes[index].insert(stored);
                    iterator.remove();
                }
            }
        }
    }

    public void retrieve(final List<QuadObj> result, final QuadObj entry) {
        int index = getIndex(entry.bb());
        if (nodes[0] != null) {
            if (index != -1) {
                nodes[index].retrieve(result, entry);
            } else {
                for (QuadTree node : nodes) {
                    if (node != null && node.intersects(entry.bb())) {
                        node.retrieve(result, entry);
                    }
                }
            }
        }

        result.addAll(objects);
    }

    public boolean intersects(final BoundingBox bb) {
        return this.bounds.intersects(bb);
    }

    private void split() {
        double subWidth = bounds.width() / 2.0;
        double subHeight = bounds.height() / 2.0;
        double x = bounds.minX();
        double y = bounds.minY();

        nodes[0] = new QuadTreeImpl(level + 1, new BoundingBox(x + subWidth, y, x + 2 * subWidth, y + subHeight));
        nodes[1] = new QuadTreeImpl(level + 1, new BoundingBox(x, y, x + subWidth, y + subHeight));
        nodes[2] = new QuadTreeImpl(level + 1, new BoundingBox(x, y + subHeight, x + subWidth, y + 2 * subHeight));
        nodes[3] = new QuadTreeImpl(level + 1,
                new BoundingBox(x + subWidth, y + subHeight, x + 2 * subWidth, y + 2 * subHeight));
    }

    private int getIndex(final BoundingBox box) {
        double midPointVert = bounds.minX() + bounds.width() / 2.0;
        double midPointOriz = bounds.minY() + bounds.height() / 2.0;

        boolean topQuadrant = box.maxY() <= midPointOriz;
        boolean bottomQuadrant = box.minY() >= midPointOriz;

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