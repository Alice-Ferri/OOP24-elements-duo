package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.map.api.TileType;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

public class Fireboy extends AbstractPlayer implements Player {
    public Fireboy(final Position startPos) { super(startPos); }

    @Override public PlayerType getType() { return PlayerType.FIREBOY; }

    @Override
    public boolean canWalkOn(final TileType type) {
        return switch (type) {
            case FLOOR, FIREEXIT, WATEREXIT, FIRESPAWN, WATERSPAWN -> true;
            case WALL -> false;
        };
    }

    @Override
    public boolean isFatal(final TileType type) {
        // richiesta: Fireboy muore se tocca WATER_SPAWN (oltre che eventuale acqua, se vuoi)
        return type == TileType.WATERSPAWN;
    }

    @Override
    public TileType getGoalExitType() { return TileType.FIREEXIT; }
}