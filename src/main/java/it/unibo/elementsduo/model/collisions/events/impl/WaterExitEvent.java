package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

public class WaterExitEvent implements Event {
    private final Player player;

    public WaterExitEvent(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }
}