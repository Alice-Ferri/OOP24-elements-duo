package it.unibo.elementsduo.model.collisionevents.impl;

import it.unibo.elementsduo.model.collisionevents.api.Event;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

public class GemCollectedEvent implements Event {
    private final Player player;
    private final Gem gem;

    public GemCollectedEvent(final Player p, final Gem g) {
        this.player = p;
        this.gem = g;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Gem getGem() {
        return this.gem;
    }
}
