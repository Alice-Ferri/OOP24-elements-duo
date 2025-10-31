package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.GemCollectedCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

public class GemCollisionsHandler extends AbstractCollisionHandler<Player, Gem> {

    private final EventManager eventManager;

    public GemCollisionsHandler(EventManager em) {
        super(Player.class, Gem.class);
        this.eventManager = em;
    }

    @Override
    public void handleCollision(Player player, Gem gem, CollisionInformations c, CollisionResponse.Builder builder) {
        builder.addLogicCommand(new GemCollectedCommand(player, gem, eventManager));
    }

}
