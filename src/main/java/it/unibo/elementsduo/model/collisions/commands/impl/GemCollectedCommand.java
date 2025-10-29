package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

public class GemCollectedCommand implements CollisionCommand {

    private final Player player;
    private final Gem gem;
    private final EventManager eventManager;

    public GemCollectedCommand(Player player, Gem gem, EventManager eventManager) {
        this.player = player;
        this.gem = gem;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        if (this.gem.isActive()) {
            gem.collect();
            this.eventManager.notify(new GemCollectedEvent(player, gem));
        }
    }

}
