package it.unibo.elementsduo.model.Obstacles;

import it.unibo.elementsduo.resources.Position;

public class ObstacleFactory {
    public Obstacle createObstacle(final ObstacleType.type type, final Position pos) {
        switch (type) {
            case WATER_POOL:
                return new WaterPool(pos);
            case LAVA_POOL:
                return new LavaPool(pos);

            default:
                throw new IllegalArgumentException("no obstacle");
        }
    }
}