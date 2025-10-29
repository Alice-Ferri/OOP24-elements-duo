package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerHazardCommand implements CollisionCommand {

    private final Player player;
    private final Hazard hazard;
    private final EventManager eventManager;

    public PlayerHazardCommand(Player player, Hazard hazard, EventManager eventManager) {
        this.player = player;
        this.hazard = hazard;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        this.eventManager.notify(new PlayerDiedEvent(this.player));
    }

}
