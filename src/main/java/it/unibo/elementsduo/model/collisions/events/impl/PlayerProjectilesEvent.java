package it.unibo.elementsduo.model.collisions.events.impl; // O il tuo package eventi

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

public class PlayerProjectilesEvent implements Event {

    private final Player player;
    private final Projectiles projectile;

    public PlayerProjectilesEvent(final Player player, final Projectiles projectile) {
        this.player = player;
        this.projectile = projectile;
    }

    public Player getPlayer() {
        return player;
    }

    public Projectiles getProjectile() {
        return projectile;
    }
}