package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.enemies.api.Enemy;
import it.unibo.elementsduo.model.collisions.commands.impl.PlayerEnemyCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.player.api.Player;
import it.unibo.elementsduo.resources.Vector2D;

public class PlayerEnemyHandler extends AbstractCollisionHandler<Player, Enemy> {

    private static final double VERTICAL_THRESHOLD = -0.5;
    private final EventManager eventManager;

    public PlayerEnemyHandler(EventManager em) {
        super(Player.class, Enemy.class);
        this.eventManager = em;
    }

    @Override
    public void handleCollision(Player player, Enemy enemy, CollisionInformations c,
            CollisionResponse.Builder builder) {
        Vector2D normalEnemyPlayer = calculateNormalFromPlayerPerspective(c);
        boolean isPlayerAboveEnemy = normalEnemyPlayer.y() < VERTICAL_THRESHOLD;

        builder.addLogicCommand(
                new PlayerEnemyCommand(player, enemy, eventManager, isPlayerAboveEnemy));
    }

    private Vector2D calculateNormalFromPlayerPerspective(CollisionInformations c) {
        return (c.getObjectA() instanceof Player)
                ? c.getNormal()
                : c.getNormal().multiply(-1);
    }

}