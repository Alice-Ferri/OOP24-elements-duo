package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.collisions.commands.impl.PlayerEnemyCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PlayerEnemyHandler extends AbstractCollisionHandler<Player, Enemy> {

    private final EventManager eventManager;

    public PlayerEnemyHandler(EventManager em) {
        super(Player.class, Enemy.class);
        this.eventManager = em;
    }

    @Override
    public void handleCollision(Player player, Enemy enemy, CollisionInformations c,
            CollisionResponse collisionResponse) {
        Vector2D normalEnemyPlayer;
        if (c.getObjectA() instanceof Player) {
            normalEnemyPlayer = c.getNormal();
        } else {
            normalEnemyPlayer = c.getNormal().multiply(-1);
        }

        boolean isOn;
        if (normalEnemyPlayer.y() < -0.5)
            isOn = true;
        else
            isOn = false;

        if (isOn) {
            collisionResponse.addLogicCommand(new PlayerEnemyCommand(player, enemy, eventManager, true));
        } else {
            collisionResponse.addLogicCommand(new PlayerEnemyCommand(player, enemy, eventManager, false));
        }
    }

}
