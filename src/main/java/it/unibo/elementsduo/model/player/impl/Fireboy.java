package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

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
            case FLOOR, FIREEXIT, WATEREXIT, FIRESPAWN, WATERSPAWN -> true;
            case WALL -> false;
        };
    }

    @Override
    public boolean isFatal(final obstacleType.type obstacle obstacle) {
        return type == TileType.WATERSPAWN;
    }

    @Override
    public obstacleType.type obstacle getGoalExitType() {
        return obstacleType.type.FIREEXIT;
    }
}