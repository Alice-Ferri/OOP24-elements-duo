package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;

public interface Toggler extends Collidable {
    void toggle();
}