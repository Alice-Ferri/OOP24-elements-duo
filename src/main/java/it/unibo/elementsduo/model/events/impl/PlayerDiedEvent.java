package it.unibo.elementsduo.model.events.impl;

import java.util.EventListener;

import it.unibo.elementsduo.model.events.api.Event;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerDiedEvent implements Event {
    private final Player player;

    public PlayerDiedEvent(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return this.player;
    }
}
