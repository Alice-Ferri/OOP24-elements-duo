package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.impl.HazardType;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.exitZone.impl.ExitType;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.model.player.api.handlers.PlayerCollsCorrectorHandler;
import it.unibo.elementsduo.model.player.api.handlers.PlayerInputHandler;
import it.unibo.elementsduo.model.player.api.handlers.PlayerPhysicsHandler;
import it.unibo.elementsduo.resources.Position;

/**
 * Represents the Fireboy player character.
 */
public class Fireboy extends AbstractPlayer {

    /**
     * Constructs a Fireboy with all injected handlers.
     *
     * @param startPos starting position of the player
     *
     * @param collisionHandler collision strategy
     *
     * @param physicsHandler physics strategy
     *
     * @param inputHandler input handling strategy
     */
    public Fireboy(
            final Position startPos,
            final PlayerPhysicsHandler physicsHandler,
            final PlayerInputHandler inputHandler,
            final PlayerCollsCorrectorHandler collisionHandler
        ) {
        super(startPos, physicsHandler, inputHandler, collisionHandler);
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

    /**
     * Returns the specific exit type for this player.
     *
     * @return exit type of the player
     */
    @Override
    public ExitType getRequiredExitType() {
        return ExitType.FIRE_EXIT;
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
        return hazardType == HazardType.LAVA;
    }

}
