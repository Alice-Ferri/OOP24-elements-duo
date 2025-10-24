package it.unibo.elementsduo.model.player.impl;

import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;
import it.unibo.elementsduo.resources.Position;

public class Watergirl extends AbstractPlayer {

    public Watergirl(final Position startPos) {
        super(startPos);
    }

    @Override public PlayerType getPlayerType() {
        return PlayerType.WATERGIRL;
    }

}
