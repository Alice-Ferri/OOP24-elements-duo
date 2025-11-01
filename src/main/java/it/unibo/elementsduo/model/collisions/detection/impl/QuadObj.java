package it.unibo.elementsduo.model.collisions.detection.impl;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

public record QuadObj(Collidable collidable, BoundingBox bb, int index) {

}
