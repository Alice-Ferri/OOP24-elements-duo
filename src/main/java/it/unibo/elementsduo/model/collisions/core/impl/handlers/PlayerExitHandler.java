package it.unibo.elementsduo.model.collisions.core.impl.handlers;

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

public class PlayerExitHandler extends AbstractCollisionHandler<Player, ExitZone> {

    private final EventManager eventManager;

    public PlayerExitHandler(EventManager eventManager) {
        super(Player.class, ExitZone.class);
        this.eventManager = eventManager;
    }

    public void handleCollision(Player player, ExitZone exitZone, CollisionInformations collisionInfo,
            CollisionResponse.Builder builder) {

        boolean correctExit = (player.getPlayerType() == PlayerType.FIREBOY && exitZone instanceof fireExit) ||
                (player.getPlayerType() == PlayerType.WATERGIRL && exitZone instanceof waterExit);

        if (correctExit) {
            if (!player.isOnExit()) {
                player.setOnExit(true);
                exitZone.activate();

                if (player.getPlayerType() == PlayerType.FIREBOY) {
                    builder.addLogicCommand(() -> this.eventManager.notify(new FireExitEvent(player)));
                } else {
                    builder.addLogicCommand(() -> this.eventManager.notify(new WaterExitEvent(player)));
                }
            }
        }
    }
}