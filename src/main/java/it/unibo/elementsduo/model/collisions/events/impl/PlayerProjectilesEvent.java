package it.unibo.elementsduo.model.collisions.events.impl; // O il tuo package eventi

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

public record PlayerProjectilesEvent(Player player, Projectiles projectile) implements Event {

}