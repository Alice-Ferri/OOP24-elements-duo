package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.player.api.Player;

/**
 * Event triggered when a {@link Player} is hit by a {@link Projectiles}.
 * 
 * <p>
 * Used to notify that a projectile has successfully collided with and damaged a
 * player.
 *
 * @param player     the player who was hit
 * @param projectile the projectile that caused the collision
 */
public record PlayerProjectilesEvent() implements Event {
}
