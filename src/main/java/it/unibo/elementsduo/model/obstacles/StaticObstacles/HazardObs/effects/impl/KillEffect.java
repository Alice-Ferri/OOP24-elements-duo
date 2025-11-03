package it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.impl;

import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.HazardObs.effects.api.HazardEffect;
import it.unibo.elementsduo.model.player.api.Player;

public class KillEffect implements HazardEffect {
    @Override
    public void apply(Player p, EventManager em) {
        em.notify(new PlayerDiedEvent(p));
    }
}