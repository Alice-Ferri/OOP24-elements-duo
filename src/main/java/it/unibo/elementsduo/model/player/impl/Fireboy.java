package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

public class Fireboy extends AbstractPlayer {

    public Fireboy(final Position startPos) {
        super(startPos);
    }

    @Override public PlayerType getPlayerType() {
        return PlayerType.FIREBOY;
    }

    @Override
    public Type getRequiredExitType() {
        return ObstacleType.Type.FIRE_EXIT;
    }

    @Override
    public boolean isImmuneTo(final Hazard hazardType) {
        return hazardType instanceof LavaPool;
    }

}