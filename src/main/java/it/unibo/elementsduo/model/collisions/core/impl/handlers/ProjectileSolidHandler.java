package it.unibo.elementsduo.model.collisions.core.impl.handlers;

import it.unibo.elementsduo.model.collisions.core.api.Collidable;
import it.unibo.elementsduo.model.collisions.core.api.CollisionHandler;
import it.unibo.elementsduo.model.collisions.core.api.CollisionInformations;
import it.unibo.elementsduo.model.collisions.core.impl.CollisionRespinse;
import it.unibo.elementsduo.model.collisions.events.impl.EventManager;
import it.unibo.elementsduo.model.collisions.events.impl.ProjectileSolidEvent;
import it.unibo.elementsduo.model.enemies.api.Projectiles;
import it.unibo.elementsduo.model.obstacles.StaticObstacles.api.StaticObstacle;
import it.unibo.elementsduo.model.obstacles.api.obstacle;

public class ProjectileSolidHandler implements CollisionHandler {

    EventManager eventManager;

    public ProjectileSolidHandler(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public boolean canHandle(Collidable a, Collidable b) {
        return (a instanceof Projectiles && b instanceof obstacle)
                || (b instanceof Projectiles && a instanceof obstacle);
    }

    @Override
    public void handle(CollisionInformations c, CollisionRespinse collisionResponse) {
        Projectiles projectile;
        if (c.getObjectA() instanceof Projectiles) {
            projectile = (Projectiles) c.getObjectA();
        } else {
            projectile = (Projectiles) c.getObjectB();
        }

        this.eventManager.notify(new ProjectileSolidEvent(projectile));
    }

}
