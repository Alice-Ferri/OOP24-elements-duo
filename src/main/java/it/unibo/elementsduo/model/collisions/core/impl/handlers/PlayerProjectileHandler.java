package it.unibo.elementsduo.model.collisions.core.impl.handlers; // O il tuo package handlers

import it.unibo.elementsduo.model.collisions.commands.impl.PlayerProjectileCommand; // Importa il nuovo comando
import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerProjectileHandler implements CollisionHandler {

    private final EventManager eventManager;

    public PlayerProjectileHandler(final EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public boolean canHandle(final Collidable a, final Collidable b) {
        return (a instanceof Player && b instanceof Projectiles)
                || (b instanceof Player && a instanceof Projectiles);
    }

    @Override
    public void handle(final CollisionInformations c, final CollisionResponse response) {
        final Player player;
        final Projectiles projectile;

        if (c.getObjectA() instanceof Player) {
            player = (Player) c.getObjectA();
            projectile = (Projectiles) c.getObjectB();
        } else {
            player = (Player) c.getObjectB();
            projectile = (Projectiles) c.getObjectA();
        }

        response.addLogicCommand(
                new PlayerProjectileCommand(player, projectile, this.eventManager));

    }

}