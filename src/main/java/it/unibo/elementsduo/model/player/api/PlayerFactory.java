package it.unibo.elementsduo.model.player.api;

import it.unibo.elementsduo.resources.Position;

/**
 * Factory interface for creating Player instances.
 */
@FunctionalInterface
public interface PlayerFactory {

    /**
     * Creates a new Player of the given type.
     *
     * @param playerType the type of player
     *
     * @param startPos the starting position of the player
     *
     * @return a new instance of Player
     */
    Player createPlayer(PlayerType playerType, Position startPos);
}
