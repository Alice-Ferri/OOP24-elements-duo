package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.ExitType;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents the Fireboy player character.
 */
public class Fireboy extends AbstractPlayer {

    /**
     * Creates a new Fireboy starting from the given position.
     *
     * @param startPos the initial position of the player
     */
    public Fireboy(final Position startPos) {
        super(startPos);
    }

    /**
     * Returns the specific player type for this character.
     *
     * @return {@link PlayerType#FIREBOY}
     */
    @Override
    public PlayerType getPlayerType() {
        return PlayerType.FIREBOY;
    }

    @Override
    public ExitType getRequiredExitType() {
        return ExitType.FIRE_EXIT;
    }

    @Override
    public boolean isImmuneTo(HazardType hazardType) {
        return hazardType == HazardType.LAVA;
    }

}
