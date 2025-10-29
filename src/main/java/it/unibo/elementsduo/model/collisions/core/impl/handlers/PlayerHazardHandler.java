package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PlayerHazardCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

public class PlayerHazardHandler implements CollisionHandler {

    EventManager eventManager;

    public PlayerHazardHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Hazard) || (b instanceof Player && a instanceof Hazard);
    }

    @Override
    public void handle(CollisionInformations c, CollisionResponse collisionResponse) {
        Player player = null;
        Hazard hazard = null;
        if (c.getObjectA() instanceof Player && c.getObjectB() instanceof Hazard) {
            player = (Player) c.getObjectA();
            hazard = (Hazard) c.getObjectB();
        } else if (c.getObjectA() instanceof Hazard && c.getObjectB() instanceof Player) {
            player = (Player) c.getObjectB();
            hazard = (Hazard) c.getObjectA();
        }

        if (player == null || hazard == null)
            return;

        if (player instanceof Fireboy && hazard instanceof waterPool) {
            collisionResponse.addCommand(new PlayerHazardCommand(player, hazard, eventManager));
        } else if (player instanceof Watergirl && hazard instanceof lavaPool) {
            collisionResponse.addCommand(new PlayerHazardCommand(player, hazard, eventManager));
        } else if (hazard instanceof greenPool) {
            collisionResponse.addCommand(new PlayerHazardCommand(player, hazard, eventManager));
        }
    }

}
