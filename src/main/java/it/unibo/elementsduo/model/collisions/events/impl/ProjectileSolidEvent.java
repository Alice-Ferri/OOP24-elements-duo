package it.unibo.elementsduo.model.collisions.events.impl;

import it.unibo.elementsduo.model.collisions.events.api.Event;
import it.unibo.elementsduo.model.enemies.api.Projectiles;

/**
 * Event triggered when a {@link Projectiles} collides with a solid object.
 * 
 * <p>
 * Used to notify that a projectile has hit a wall, platform, or other solid
 * obstacle
 * and should be deactivated or destroyed.
 *
 * @param projectile the projectile that collided with a solid object
 */
public record ProjectileSolidEvent() implements Event {
}
