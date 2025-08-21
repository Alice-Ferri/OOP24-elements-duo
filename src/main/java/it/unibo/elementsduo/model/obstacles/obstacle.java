package it.unibo.elementsduo.model.obstacles;

import it.unibo.elementsduo.resources.Position;

public abstract class obstacle {
    private final Position position;

    public obstacle(final Position position) {
        this.position = position;
    }
}