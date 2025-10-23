package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.impl.obstacleType;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.utils.Position;

public class Watergirl extends AbstractPlayer {

    public Watergirl(final Position startPos) {
        super(startPos);
    }

    @Override public PlayerType getType() {
        return PlayerType.WATERGIRL;
    }

    @Override
    public boolean canWalkOn(final obstacleType.type obstacle) {
        return switch (obstacle) {
            case FLOOR, FIRE_EXIT, WATER_EXIT, FIRE_SPAWN, GREEN_POOL, LAVA_POOL, WATER_POOL, WATER_SPAWN -> true;
            case WALL -> false;
        };
    }

    @Override
    public boolean isFatal(final obstacleType.type typeMap) {
        return typeMap == obstacleType.type.FIRE_SPAWN;
    }

    @Override
    public obstacleType.type getGoalExitType() {
        return obstacleType.type.WATER_EXIT;
    }

}