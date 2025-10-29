package it.unibo.elementsduo.model.collisions.commands.impl;

import it.unibo.elementsduo.model.collisions.commands.api.CollisionCommand;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerDiedEvent;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerProjectilesEvent;
import it.unibo.elementsduo.model.collisions.events.impl.PlayerProjectilesEvent;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerProjectileCommand implements CollisionCommand {

    private final Player player;
    private final Projectiles projectile;
    private final EventManager eventManager;

    public PlayerProjectileCommand(final Player player, final Projectiles projectile, final EventManager eventManager) {
        this.player = player;
        this.projectile = projectile;
        this.eventManager = eventManager;
    }

    @Override
    public void execute() {
        this.eventManager.notify(new PlayerProjectilesEvent(this.player, this.projectile));
    }
}