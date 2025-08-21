package it.unibo.elementsduo.model.obstacles;

import it.unibo.elementsduo.resources.Position;

public class obstacleFactory {
    public obstacle createObstacle(final obstacleType.type type, final Position pos) {
        switch (type) {
            case WATER_POOL:
                return new waterPool(pos);
            case LAVA_POOL:
                return new lavaPool(pos);

            default:
                throw new IllegalArgumentException("no obstacle");
        }
    }
}