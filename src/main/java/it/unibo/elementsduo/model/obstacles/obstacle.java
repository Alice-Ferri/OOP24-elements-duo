package it.unibo.elementsduo.model.Obstacles;

import it.unibo.elementsduo.resources.Position;

public abstract class Obstacle {
    private final Position position;

    public Obstacle(final Position position) {
        this.position = position;
    }
}
