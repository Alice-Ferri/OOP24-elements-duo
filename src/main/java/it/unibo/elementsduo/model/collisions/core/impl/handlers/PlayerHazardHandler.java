package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PlayerHazardCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Hazard;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.greenPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.lavaPool;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.HazardObs.waterPool;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.impl.Fireboy;
import it.unibo.elementsduo.model.player.impl.Watergirl;

public class PlayerHazardHandler extends AbstractCollisionHandler<Player, Hazard> {

    EventManager eventManager;

    public PlayerHazardHandler(EventManager eventManager) {
        super(Player.class, Hazard.class);
        this.eventManager = eventManager;
    }

    @Override
    public void handleCollision(Player player, Hazard hazard, CollisionInformations c,
            CollisionResponse collisionResponse) {
        if (player instanceof Fireboy && hazard instanceof waterPool) {
            collisionResponse.addLogicCommand(new PlayerHazardCommand(player, hazard, eventManager));
        } else if (player instanceof Watergirl && hazard instanceof lavaPool) {
            collisionResponse.addLogicCommand(new PlayerHazardCommand(player, hazard, eventManager));
        } else if (hazard instanceof greenPool) {
            collisionResponse.addLogicCommand(new PlayerHazardCommand(player, hazard, eventManager));
        }
    }

}
