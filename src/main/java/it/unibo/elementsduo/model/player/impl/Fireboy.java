package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.api.obstacle;
import it.unibo.elementsduo.model.obstacles.impl.obstacleType;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.utils.Position;

public class Fireboy extends AbstractPlayer {

    public Fireboy(final Position startPos) {
        super(startPos);
    }

    @Override public PlayerType getType() {
        return PlayerType.FIREBOY;
    }

    @Override
    public boolean canWalkOn(final obstacleType.type obstacle) {
        return switch (obstacle) {
            case WATER_POOL, LAVA_POOL, GREEN_POOL, FLOOR, WATER_EXIT, FIRE_EXIT, WATER_SPAWN, FIRE_SPAWN-> true;
            case WALL -> false;
        };
    }

    @Override
    public boolean isFatal(final obstacleType.type typeMap) {
        return typeMap == obstacleType.type.WATER_POOL;
    }

    @Override
    public obstacleType.type getGoalExitType() {
        return obstacleType.type.FIRE_EXIT;
    }

}