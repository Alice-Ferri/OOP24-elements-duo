package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.GemCollectedCommand;
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.GemCollectedEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.Gem;
import it.unibo.elementsduo.model.player.api.Player;

public class GemCollisionsHandler implements CollisionHandler {

    private final EventManager eventManager;

    public GemCollisionsHandler(EventManager em) {
        this.eventManager = em;
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof Gem) || (b instanceof Player && a instanceof Gem);
    }

    @Override
    public void handle(CollisionInformations c, CollisionResponse collisionResponse) {
        final Player player;
        final Gem gem;
        if (c.getObjectA() instanceof Player) {
            player = (Player) c.getObjectA();
            gem = (Gem) c.getObjectB();
        } else {
            player = (Player) c.getObjectB();
            gem = (Gem) c.getObjectA();
        }

        collisionResponse.addCommand(new GemCollectedCommand(player, gem, eventManager));
    }

}
