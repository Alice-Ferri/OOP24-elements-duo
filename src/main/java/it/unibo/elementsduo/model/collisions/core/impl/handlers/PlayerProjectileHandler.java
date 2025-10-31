package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.PlayerProjectileCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerProjectileHandler extends AbstractCollisionHandler<Player, Projectiles> {

    private final EventManager eventManager;

    public PlayerProjectileHandler(final EventManager eventManager) {
        super(Player.class, Projectiles.class);
        this.eventManager = eventManager;
    }

    @Override
    public void handleCollision(Player player, Projectiles projectile, final CollisionInformations c,
            final CollisionResponse response) {
        response.addLogicCommand(
                new PlayerProjectileCommand(player, projectile, this.eventManager));

    }

}