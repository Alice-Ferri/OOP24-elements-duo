package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.ExitType;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents the Watergirl player character.
 */
public class Watergirl extends AbstractPlayer {

    /**
     * Creates a new Watergirl starting from the given position.
     *
     * @param startPos the initial position of the player
     */
    public Watergirl(final Position startPos) {
        super(startPos);
    }

    /**
     * Returns the specific player type for this character.
     *
     * @return {@link PlayerType#WATERGIRL}
     */
    @Override
    public PlayerType getPlayerType() {
        return PlayerType.WATERGIRL;
    }

    @Override
    public ExitType getRequiredExitType() {
        return ExitType.WATER_EXIT;
    }

    @Override
    public boolean isImmuneTo(HazardType hazardType) {
        return hazardType == HazardType.WATER;
    }

}
