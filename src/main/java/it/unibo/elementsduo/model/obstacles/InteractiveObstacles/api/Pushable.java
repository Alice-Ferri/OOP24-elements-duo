package it.unibo.elementsduo.model.obstacles.InteractiveObstacles.api;

import it.unibo.elementsduo.resources.Vector2D;

// interface for the button 

public interface Pushable {
    void push(Vector2D v);

    void move(Vector2D delta);
}
