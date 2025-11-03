package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.ExitType;
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

    /**
     * Returns the specific exit type for this player.
     *
     * @return exit type of the player
     */
    @Override
    public ExitType getRequiredExitType() {
        return ExitType.WATER_EXIT;
    }

    /**
     * Checks if this player is immune to the object.
     *
     * @param hazardType obstacle to check
     *
     * @return true if is immune to the hazard type, false otherwise
     */
    @Override
    public boolean isImmuneTo(final HazardType hazardType) {
        return hazardType == HazardType.WATER;
    }


}
