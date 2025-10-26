package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.Enemies.Enemy;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.events.impl.EventManager;
import it.unibo.elementsduo.model.events.impl.PlayerDiedEvent;

public class PlayerEnemyHandler implements CollisionHandler {

    private final EventManager eventManager;

    public PlayerEnemyHandler(EventManager em) {
        this.eventManager = em;
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Enemy) || (b instanceof Player && a instanceof Enemy);
    }

    @Override
    public void handle(CollisionInformations c) {
        final Player p;
        if (c.getObjectA() instanceof Player) {
            p = (Player) c.getObjectA();
        } else {
            p = (Player) c.getObjectB();
        }

        if (p != null) {
            this.eventManager.notify(new PlayerDiedEvent(p));
        }
    }

}
