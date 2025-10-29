package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.FireExitEvent;
import it.unibo.elementsduo.model.collisions.events.impl.WaterExitEvent;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.ExitZone;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.fireExit;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.impl.exit.waterExit;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.model.player.api.PlayerType;

public class PlayerExitHandler implements CollisionHandler {

    private final EventManager eventManager;

    public PlayerExitHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Player && b instanceof ExitZone) || (a instanceof ExitZone && b instanceof Player);
    }

    @Override
    public void handle(CollisionInformations collisionInfo,CollisionResponse c) {
        Player player = null;
        ExitZone exit = null;

        if (collisionInfo.getObjectA() instanceof Player && collisionInfo.getObjectB() instanceof ExitZone) {
            player = (Player) collisionInfo.getObjectA();
            exit = (ExitZone) collisionInfo.getObjectB();
        } else if (collisionInfo.getObjectA() instanceof ExitZone && collisionInfo.getObjectB() instanceof Player) {
            exit = (ExitZone) collisionInfo.getObjectA();
            player = (Player) collisionInfo.getObjectB();
        }

        if (player == null || exit == null ) {
            return;
        }

        boolean correctExit = (player.getPlayerType() == PlayerType.FIREBOY && exit instanceof fireExit) ||
                              (player.getPlayerType() == PlayerType.WATERGIRL && exit instanceof waterExit);

        if (correctExit) {
            if (!player.isOnExit()) {
                player.setOnExit(true); 
                exit.activate();

                if (player.getPlayerType() == PlayerType.FIREBOY) {
                    this.eventManager.notify(new FireExitEvent(player));
                } else {
                    this.eventManager.notify(new WaterExitEvent(player));
                }
            }
            
        }
    }
}