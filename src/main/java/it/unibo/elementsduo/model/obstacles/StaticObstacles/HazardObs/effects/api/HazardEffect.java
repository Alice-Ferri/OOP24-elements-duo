package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.api;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;

public interface HazardEffect {
    void apply(Player p, EventManager em);
}