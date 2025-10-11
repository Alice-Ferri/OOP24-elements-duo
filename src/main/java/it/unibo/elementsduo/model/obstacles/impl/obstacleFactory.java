package it.unibo.elementsduo.model.obstacles.impl;

import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.utils.Position;

public class obstacleFactory {
    public obstacle createObstacle(final obstacleType.type type, final Position pos) {
        switch (type) {
            case WATER_POOL:
                return new waterPool(pos);
            case LAVA_POOL:
                return new lavaPool(pos);
            case GREEN_POOL:
                return new greenPool(pos);
            case WALL:
                return new Wall(pos);
            case FLOOR:
                return new Floor(pos);
            case WATER_SPAWN:
                return new waterSpawn(pos);
             case FIRE_SPAWN:
                return new fireSpawn(pos);
            case FIRE_EXIT:
                return new fireExit(pos);
            case WATER_EXIT:
                return new waterExit(pos);
            default:
                throw new IllegalArgumentException("no obstacle");
        }
    }
}