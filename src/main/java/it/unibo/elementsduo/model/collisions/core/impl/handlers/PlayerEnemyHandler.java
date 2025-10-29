package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionRespinse;
import it.unibo.elementsduo.model.collisions.events.impl.EnemyDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

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
    public void handle(CollisionInformations c, CollisionRespinse collisionResponse) {
        final Player player;
        final Enemy enemy;
        Vector2D normal = c.getNormal();
        if (c.getObjectA() instanceof Player) {
            player = (Player) c.getObjectA();
            enemy = (Enemy) c.getObjectB();
        } else {
            player = (Player) c.getObjectB();
            enemy = (Enemy) c.getObjectA();
            normal = normal.multiply(-1);
        }

        if (player == null) {
            return;
        }

        boolean isOn;
        if (normal.y() < -0.5)
            isOn = true;
        else
            isOn = false;

        if (isOn) {
            this.eventManager.notify(new EnemyDiedEvent(enemy));
        } else {
            this.eventManager.notify(new PlayerDiedEvent(player));
        }
    }

}
