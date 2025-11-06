package it.unibo.elementsduo.model.collisions.detection.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

/**
 * Represents an entry inside the QuadTree structure.
 *
 * @param collidable the collidable object
 * @param bb         bounding box of the object
 * @param index      index of the object in the main list
 */
public record QuadObj(Collidable collidable, BoundingBox bb, int index) {

}
