package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.commands.impl.ProjectileSolidCommand;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionResponse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

public class ProjectileSolidHandler extends AbstractCollisionHandler<Projectiles, obstacle> {

    EventManager eventManager;

    public ProjectileSolidHandler(EventManager eventManager) {
        super(Projectiles.class, obstacle.class);
        this.eventManager = eventManager;
    }

    @Override
    public void handleCollision(Projectiles projectile, obstacle ob, CollisionInformations c,
            CollisionResponse.Builder builder) {
        builder.addLogicCommand(new ProjectileSolidCommand(projectile, eventManager));
    }

}
