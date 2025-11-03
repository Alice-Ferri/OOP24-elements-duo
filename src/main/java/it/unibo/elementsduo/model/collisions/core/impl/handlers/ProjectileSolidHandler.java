package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.Obstacle;

/**
 * Handles collisions between {@link Projectiles} and solid {@link Obstacle}s.
 * 
 * <p>
 * When a projectile collides with a solid obstacle, the projectile is
 * deactivated
 * and a {@link ProjectileSolidEvent} is dispatched through the
 * {@link EventManager}.
 */
public final class ProjectileSolidHandler extends AbstractCollisionHandler<Projectiles, Obstacle> {

    private final EventManager eventManager;

    /**
     * Creates a new {@code ProjectileSolidHandler} instance.
     *
     * @param eventManager the event manager used to notify collision events
     */
    public ProjectileSolidHandler(final EventManager eventManager) {
        super(Projectiles.class, Obstacle.class);
        this.eventManager = eventManager;
    }

    /**
     * Handles a collision between a {@link Projectiles} and an {@link Obstacle}.
     * 
     * <p>
     * Deactivates the projectile and triggers a {@link ProjectileSolidEvent}.
     *
     * @param projectile the projectile involved in the collision
     * @param ob         the obstacle that was hit
     * @param c          the collision information
     * @param builder    the collision response builder used to queue logic commands
     */
    @Override
    public void handleCollision(final Projectiles projectile, final Obstacle ob, final CollisionInformations c,
            final CollisionResponse.Builder builder) {
        projectile.deactivate();
        builder.addLogicCommand(() -> this.eventManager.notify(new ProjectileSolidEvent()));
    }
}
