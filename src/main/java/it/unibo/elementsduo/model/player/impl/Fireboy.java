package it.unibo.elementsduo.model.player.impl;

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
    @Override public PlayerType getPlayerType() {
        return PlayerType.FIREBOY;
    }

}