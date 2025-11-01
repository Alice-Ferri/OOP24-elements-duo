package it.unibo.elementsduo.model.collisions.detection.api;

import java.util.List;

import it.unibo.elementsduo.model.collisions.detection.impl.BoundingBox;
import it.unibo.elementsduo.model.collisions.detection.impl.QuadObj;

public interface QuadTree {
    void insert(QuadObj entry);

    void retrieve(List<QuadObj> result, QuadObj entry);

    boolean intersects(BoundingBox bb);
}
